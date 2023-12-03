# Manifestos Kubernetes

Este diretório contém os manifestos utilizados para realizar o deploy do microsserviço dentro do cluster kubernetes.

## Secret
Dentro do arquivo `adote-secret.yaml` estão as credenciais de acesso ao banco de dados criado, o RDS. Cada variável deve estar em formato de base64 para ser aceito pelo kubernetes.

Este arquivo será lido pelo deployment para poder conectar o microsserviço ao banco de dados.

## Deployment

Dentro do arquivo `adote-deployment.yaml` estão todas as configurações necessárias para realizar o deploy do microsserviço, o único ponto a ser alterado é na linha `25` que contém a `image`.

Visto que o código fonte também está presente neste repositório na pasta [adote-application](../../../../adote-application/), você mesmo pode executar o build da imagem docker e salvá-la no repositório que preferir. As instruções de como fazer o build da imagem, estão presentes no [README.md](../../../../adote-application/README.md) do microsserviço.

## Service

Dentro do arquivo `adote-service.yaml` estão as configurações de acesso, como as portas que o microsserviço escuta e também a configuração de `LoadBalancer` a ser criado, neste caso um Network Load Balancer.

## Instalação

Para realizar a instalação dos arquivos, basta executar o seguinte comando neste diretório:

```bash
kubectl apply -f adote-secret.yaml
kubectl apply -f adote-deployment.yaml
kubectl apply -f adote-service.yaml
```

Os recursos serão criados dentro do namespace `default` do cluster e para removê-los é o seguinte:

```bash
kubectl delete -f adote-secret.yaml
kubectl delete -f adote-deployment.yaml
kubectl delete -f adote-service.yaml
```