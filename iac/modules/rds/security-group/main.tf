resource "aws_security_group" "default" {
  name          = var.name
  description   = "tcc security group"
  vpc_id        = var.vpc_id

  ingress {
    description      = "Postgres"
    from_port        = 5432
    to_port          = 5432
    protocol         = "tcp"
    cidr_blocks      = [var.cidr]
  }

  tags = {
    Name = var.name
  }
}