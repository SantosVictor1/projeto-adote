resource "aws_iam_role" "this" {
  name = var.eks_cluster_role_name

  assume_role_policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "eks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "aws_eks_cluster_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.this.id
}

resource "aws_iam_role_policy_attachment" "aws_eks_service_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSServicePolicy"
  role       = aws_iam_role.this.id
}

resource "aws_iam_policy" "this" {
  name = "DescribeAccountAttributes-EKS"
  path = "/"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": "iam:CreateServiceLinkedRole",
            "Resource": "arn:aws:iam::*:role/aws-service-role/*"
        },
        {
            "Effect": "Allow",
            "Action": [
                "ec2:DescribeAccountAttributes",
                "ec2:DescribeInternetGateways"
            ],
            "Resource": "*"
        }
    ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "aws_describe_account_attributes" {
  policy_arn = aws_iam_policy.this.arn
  role       = aws_iam_role.this.id
}

resource "aws_security_group" "eks_master" {
  name        = var.security_group_name
  description = "SG for cluster nodes access cluster API."
  vpc_id      = var.aws_vpc_id

  tags = {
    Name = var.security_group_name
  }
}

resource "aws_security_group" "eks_nodes" {
  name        = var.security_group_name_nodes
  description = "Security group for all nodes in the cluster"
  vpc_id      = var.aws_vpc_id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name                                            = var.security_group_name_nodes
    "kubernetes.io/cluster/${var.eks_cluster_name}" = "owned"
  }
}

resource "aws_security_group_rule" "endpoint_master" {
  security_group_id        = aws_security_group.eks_master.id
  type                     = "ingress"
  from_port                = 443
  to_port                  = 443
  protocol                 = "tcp"
  cidr_blocks              = flatten([[var.cidr_blocks]])
}

resource "aws_security_group_rule" "cluster_inbound" {
  description              = "Allow worker nodes to communicate with the cluster API Server"
  from_port                = 443
  protocol                 = "tcp"
  security_group_id        = aws_security_group.eks_master.id
  source_security_group_id = aws_security_group.eks_nodes.id
  to_port                  = 443
  type                     = "ingress"
}

resource "aws_security_group_rule" "nodes" {
  description              = "Allow nodes to communicate with each other"
  from_port                = 0
  protocol                 = "-1"
  security_group_id        = aws_security_group.eks_nodes.id
  source_security_group_id = aws_security_group.eks_nodes.id
  to_port                  = 65535
  type                     = "ingress"
}

resource "aws_security_group_rule" "nodes_inbound" {
  description              = "Allow worker Kubelets and pods to receive communication from the cluster control plane"
  from_port                = 1025
  protocol                 = "tcp"
  security_group_id        = aws_security_group.eks_nodes.id
  source_security_group_id = aws_security_group.eks_master.id
  to_port                  = 65535
  type                     = "ingress"
}

resource "aws_eks_cluster" "this" {
  name     = var.eks_cluster_name
  role_arn = aws_iam_role.this.arn
  version  = 1.28

  vpc_config {
    security_group_ids      = [aws_security_group.eks_master.id]
    endpoint_private_access = true
    endpoint_public_access  = true
    subnet_ids              = var.eks_cluster_subnet_ids
  }

  depends_on = [
    aws_iam_role_policy_attachment.aws_eks_cluster_policy,
    aws_iam_role_policy_attachment.aws_eks_service_policy,
    aws_iam_role_policy_attachment.aws_describe_account_attributes
  ]
}

resource "aws_iam_role" "eks_nodes" {
  name = var.eks_nodes_role_name

  assume_role_policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "aws_eks_worker_node_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
  role       = aws_iam_role.eks_nodes.name
}

resource "aws_iam_role_policy_attachment" "aws_eks_cni_policy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
  role       = aws_iam_role.eks_nodes.name
}

resource "aws_iam_role_policy_attachment" "ec2_read_only" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
  role       = aws_iam_role.eks_nodes.name
}


resource "null_resource" "private_subnet_tags" {
  triggers = {
    cluster_name    = var.eks_cluster_name
    private_subnets = join(" ", var.eks_nodes_subnet_ids)
    timestamp       = timestamp()
  }
  count = length(var.eks_nodes_subnet_ids) > 0 ? length(var.eks_nodes_subnet_ids) : 0

  provisioner "local-exec" {
    command = "aws ec2 create-tags --resources ${self.triggers.private_subnets} --tags Key=kubernetes.io/cluster/${self.triggers.cluster_name},Value='shared'"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "aws ec2 delete-tags --resources ${self.triggers.private_subnets} --tags Key=kubernetes.io/cluster/${self.triggers.cluster_name},Value='shared'"
  }
}

resource "aws_eks_node_group" "this" {
  cluster_name    = aws_eks_cluster.this.name
  node_group_name = var.node_group_name
  node_role_arn   = aws_iam_role.eks_nodes.arn
  subnet_ids      = var.eks_nodes_subnet_ids
  disk_size       = var.disk_size
  instance_types  = var.instance_types
  capacity_type   = var.capacity_type

  remote_access {
    ec2_ssh_key               = data.aws_key_pair.this.key_name
    source_security_group_ids = [aws_security_group.eks_nodes.id]
  }

  scaling_config {
    desired_size = var.pvt_desired_size
    max_size     = var.pvt_max_size
    min_size     = var.pvt_min_size
  }

  tags = {
    Name = var.node_group_name
  }

  depends_on = [
    aws_iam_role_policy_attachment.aws_eks_worker_node_policy,
    aws_iam_role_policy_attachment.aws_eks_cni_policy,
    aws_iam_role_policy_attachment.ec2_read_only
  ]
}