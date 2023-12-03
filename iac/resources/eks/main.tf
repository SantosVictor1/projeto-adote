provider "aws" {
  region                    = "REGION"
  shared_credentials_files  = ["$HOME/.aws/credentials"]
  profile                   = "default"
}

terraform {
  backend "s3" {
    bucket                  = "BUCKET_NAME"
    region                  = "REGION"

    key                     = "eks/main.tfstate"
  }
}

module "aws-eks" {
  source = "../../modules/eks"

  eks_cluster_role_name     = "adote-eks-cluster-role"
  aws_vpc_id                = "VPC-ID"
  security_group_name       = "adote-sg"
  cidr_blocks               = ["10.0.0.0/16"]
  eks_cluster_name          = "adote-eks"
  eks_cluster_subnet_ids    = ["SUBNET1-ID", "SUBNET2-ID", "SUBNET3-ID"]
  security_group_name_nodes = "adote-eks-nodes-sg"
  eks_nodes_role_name       = "adote-eks-node-role"
  eks_nodes_subnet_ids      = ["SUBNET1-ID", "SUBNET2-ID", "SUBNET3-ID"]
  node_group_name           = "adote-eks-nodes"
  disk_size                 = "20"
  instance_types            = ["t3.small"]
  capacity_type             = "SPOT"
  pvt_desired_size          = 2
  pvt_min_size              = 1
  pvt_max_size              = 2
  eks_key_name              = "SSH-KEY-NAME"
  region                    = "REGION"
  aws_profile               = "default"
}
