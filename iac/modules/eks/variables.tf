variable "region" {
  default     = ""
}

variable "eks_cluster_role_name" {
  description = "EKS master cluster name"
  type        = string
}

variable "eks_nodes_role_name" {
  description = "EKS master cluster name"
  type        = string
}

variable "aws_vpc_id" {
  description = "VPC id"
  type        = string
}

variable "security_group_name" {
  description = "EKS security group name"
  type        = string
}

variable "security_group_name_nodes" {
  description = "EKS nodes security group name"
  type        = string
}

variable "cidr_blocks" {
  description = "CIDR allow access to EKS API"
  type        = list(string)
}

variable "eks_cluster_name" {
  description = "EKS cluster name"
  type        = string
}

variable "eks_cluster_subnet_ids" {
  description = "EKS subnets for master"
  type        = list(string)
}

variable "eks_nodes_subnet_ids" {
  description = "EKS subnets for nodes"
  type        = list(string)
}

variable "node_group_name" {
  description = "EKS node group name"
  type        = string
}

variable "disk_size" {
  description = "Node group disk size"
  type        = number
}

variable "instance_types" {
  description = "Node group instance type"
  type        = list(string)
}

variable "capacity_type" {
  description = "Node capacity type: SPOT or On Demand"
  type        = string
}

variable "pvt_desired_size" {
  description = "Desired node size"
  type        = number
}

variable "pvt_min_size" {
  description = "Min node size"
  type        = number
}

variable "pvt_max_size" {
  description = "Max node size"
  type        = number
}

variable "eks_key_name" {
  description = "EKS SSH key name"
  type        = string
}

variable "aws_profile" {
  description = "AWS profile"
  type        = string
}