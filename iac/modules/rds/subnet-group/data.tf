data "aws_subnets" "public_subnets" {
  filter {
    name = "tag:Name"
    values = [var.subnet_tag]
  }
}