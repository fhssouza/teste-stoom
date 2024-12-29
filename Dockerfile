# Use a imagem base do OpenJDK 8, conforme a versão do Java no pom.xml
FROM openjdk:11-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven para o diretório de trabalho
COPY target/store-1.0.jar app.jar

# Exponha a porta que o Spring Boot estará escutando
EXPOSE 8080

# Define o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]