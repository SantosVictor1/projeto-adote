# Terraform EKS Resource

Antes de executar os comandos do Terraform, é necessário alterar as variáveis no arquivo `main.tf` para as configurações de sua conta.

Com as credenciais configuradas e as ferramentas necessárias instaladas, basta executar os seguintes comandos neste diretório:

```bash
terraform init
terraform plan
terraform apply
```

Após isso, confirmar para realizar a criação do recurso e aguardar.

Quando for criado, para ter acesso ao cluster, utilize o seguinte comando para baixar o arquivo de configuração do cluster criado:

```bash
aws eks update-kubeconfig --name adote-eks
```

Quando finalizar e quiser deletar o que foi criado, utilize o seguinte comando:

```bash
terraform destroy
```