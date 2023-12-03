provider "aws" {
  region                    = "REGION"
  shared_credentials_files  = ["$HOME/.aws/credentials"]
  profile                   = "default"
}

terraform {
  backend "s3" {
    bucket                  = "BUCKET_NAME"
    region                  = "REGION"

    key                     = "rds/main.tfstate"
  }
}

module "adote_security_group" {
  source                    = "../../modules/rds/security-group"

  name                      = "rds-adote-sg"
  vpc_id                    = "VPC-ID"
  cidr                      = "0.0.0.0/0"
}

module "adote_parameter_group" {
  source                    = "../../modules/rds/parameter-group"

  name                      = "adote-parameter-group"
  family                    = "postgres15"
}

module "adote_subnet_group" {
  source                    = "../../modules/rds/subnet-group"
  
  name                      = "adote-subnet-group"
  vpc_id                    = "VPC-ID"
  subnet_tag                = "pub-*"
}

resource "aws_db_instance" "default" {
  identifier                = "rds-adote"
  db_name                   = "adote"
  allocated_storage         = 10
  engine                    = "postgres"
  engine_version            = "15.3"
  instance_class            = "db.t3.micro"
  username                  = "USERNAME"
  password                  = "PASSWORD"
  vpc_security_group_ids    = [module.adote_security_group.security_group_id]
  parameter_group_name      = module.adote_parameter_group.parameter_group_name
  db_subnet_group_name      = module.adote_subnet_group.subnet_group_name
  skip_final_snapshot       = true
  publicly_accessible       = true
  multi_az                  = false
}