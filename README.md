# Projeto Adote

## Introdução

Este projeto foi criado para a apresentação de um TCC. Todos os códigos presentes aqui, tanto `IaC` quanto o `microsserviço` foram utilizados na apresentação.

Todas as execuções foram feitas em ambiente `Linux`, especificamente a distribuição `Ubuntu 22.04`.

## Pré-requisitos

Para executar o projeto, é necessário ter as seguintes ferramentas instaladas:
- [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
- [Terraform](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli)
- [Kubectl](https://kubernetes.io/docs/tasks/tools/#kubectl)
- [Helm](https://helm.sh/docs/intro/install/)
- [Docker](https://docs.docker.com/engine/install/ubuntu/)
- Docker-Compose
- OpenJDK 17
- Maven

## Diretórios

Existem 2 diretórios principais neste repositório.

O de [IaC](./iac) que contém todos os arquivos necessários para criar o `EKS` e o `RDS`, na `AWS`, além de possuir também os manifestos utilizados para realizar o deploy da aplicação no cluster.

O segundo diretório é o [adote-application](./adote-application/) que contém o código fonte da aplicação usada de teste.