# Microserviço Adote

## Objetivo

Este microsserviço foi criado para demonstrar o funcionamento da infraestrutura, com isso ele possui apenas 3 features.

- Cadastro de Pessoa Jurídica, sendo um dos tipos `CAREGIVER`.
- Cadastro de Pet pela pessoa `CAREGIVER`.
- Upload de fotos do Pet.

Todos os recursos criados podem ser acessados via interface do [SWAGGER](http://localhost:8080/swagger-ui/index.html).

O microsserviço conta também com integração com Bucket S3 da AWS para armazenamento das imagens, portanto é necessário informar no arquivo [application.yml](./src/main/resources/application.yml) as credenciais que possuem acesso ao bucket.

## Execução

Para executar o microsserviço localmente, é necessário primeiro subir o banco de dados, `postgres`, presente no arquivo `docker-compose.yml`.

```bash
docker-compose up
```

Após isso, basta utilizar a IDE de preferência para rodar a aplicação ou se for por linha de comando, utilizar os seguintes:

```bash
mvn clean install
java -jar targe/adote-application.jar
```

Com isso, o microsserviço irá subir na porta 8080.

## Imagem Docker

Para criar a imagem docker, primeiro execute o comando `mvn clean install` do passo anterior e depois rode:

```bash
docker build -t adote-application .
```

Caso queira fazer deploy da imagem em um `registry` específico, por exemplo o `DockerHub`. Primeiro é necessário fazer o login:

```bash
docker login
```

Após isso, basta executar o comando de build, porém com a seguinte diferença:

```bash
docker build -t USER/adote-application:latest .
```

Este comando funciona também para o `ECR`, registry da AWS.

Por fim, realize o push para o registry:

```bash
docker push USER/adote-application:latest
```

Com isso, a imagem está publicada e pode ser utilizada no `EKS` via arquivo de [deployment](../iac/kubernetes/backend/adote/adote-deployment.yaml).