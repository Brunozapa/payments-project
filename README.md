# Gerenciador de Transações de Conta (payments-project)

## Requisitos

- [Docker](https://www.docker.com/get-started) (inclui Docker Compose)
- [Java 17]([https://adoptium.net/](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))
- [Gradle](https://gradle.org/install/) (para compilar o projeto, se não estiver usando o Gradle Wrapper)

## Instruções para execução local

Siga estas etapas para construir os arquivos JAR para cada serviço:

1. **Abra um terminal no diretório do projeto**.

2. **Navegue até o diretório `account`**:
    ```bash
    cd account
    ```

3. **Execute o comando para construir o JAR**:
    ```bash
    ./gradlew build
    ```
    Para windows:
    ```bash
    ./gradlew.bat build
    ```

    Este comando cria o arquivo JAR para o serviço `account-service` no diretório `build/libs`.

4. **Navegue até o diretório `transaction`**:
    ```bash
    cd ../transaction
    ```

5. **Execute o comando para construir o JAR**:
    ```bash
    ./gradlew build
    ```
    Para windows:
    ```bash
    ./gradlew.bat build
    ```

    Este comando cria o arquivo JAR para o serviço `transaction-service` no diretório `build/libs`.

## Construindo e Executando Contêineres Docker

Depois de construir os arquivos JAR, você pode construir e executar os contêineres Docker usando o Docker Compose.

1. **Navegue até o diretório raiz do projeto** onde está o arquivo `docker-compose.yml`:
    ```bash
    cd ..
    ```

2. **Construa as imagens Docker para os serviços**:
    ```bash
    docker-compose build
    ```

    Este comando lê o `docker-compose.yml` e os `Dockerfile`s dos diretórios `account` e `transaction`, construindo as imagens Docker para cada serviço.

3. **Inicie os serviços em contêineres Docker**:
    ```bash
    docker-compose up -d
    ```

    Este comando inicia todos os serviços definidos no `docker-compose.yml`. Os serviços `account-service` e `transaction-service` serão executados em contêineres separados, assim como os serviços de dependência como PostgreSQL, Kafka, e MongoDB.

4. **Verifique se os serviços estão rodando**:
    ```bash
    docker ps
    ```

    Este comando lista todos os contêineres em execução. Verifique se os contêineres `account-service` e `transaction-service` estão na lista.

## Acessando os Serviços

- **Account Service**: [http://localhost:8080](http://localhost:8080)
- **Transaction Service**: [http://localhost:8081](http://localhost:8081)
