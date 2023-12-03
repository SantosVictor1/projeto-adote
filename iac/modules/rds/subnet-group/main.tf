resource "aws_db_subnet_group" "default" {
  name            = var.name
  description     = "Subnet group with public subnets"
  subnet_ids      = toset(data.aws_subnets.public_subnets.ids)
}