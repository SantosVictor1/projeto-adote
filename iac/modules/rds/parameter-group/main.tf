resource "aws_db_parameter_group" "default" {
  name        = var.name
  description = "Tcc parameter group"
  family      = var.family
}