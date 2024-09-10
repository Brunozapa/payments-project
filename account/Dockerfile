# Use uma imagem base do Java
FROM openjdk:17-jdk

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR para o contêiner
COPY build/libs/account-service.jar account-service.jar

# Comando para executar o JAR
ENTRYPOINT ["java", "-jar", "account-service.jar"]

# Exponha a porta que o serviço vai usar
EXPOSE 8080
