
# Stoom Store - E-commerce API

Este é um projeto de e-commerce que gerencia **produtos**, **categorias** e **marcas**, com suporte para preços dinâmicos e funcionalidades de ativação/desativação de entidades. A API foi construída utilizando **Spring Boot**, **PostgreSQL**, **Swagger** e **Maven** para gerenciamento de dependências.

## Tecnologias Utilizadas

- **Spring Boot 2.3.x** - Framework para desenvolvimento da API RESTful.
- **Spring Data JPA** - Para interações com o banco de dados.
- **PostgreSQL** - Banco de dados relacional.
- **Swagger** - Para documentação da API e testes interativos.
- **Maven** - Gerenciamento de dependências e build do projeto.

## Como Executar o Projeto

### 1. **Executando com Docker (Recomendado)**

Você pode rodar o projeto junto com o PostgreSQL usando o Docker. Para isso, siga os passos abaixo:

#### Passos:
1. **Construa a imagem Docker para o aplicativo Spring Boot:**
   - Certifique-se de ter o **Docker** instalado em sua máquina.
   - No terminal, execute o seguinte comando para construir a imagem Docker:
     ```bash
     docker-compose build
     ```

2. **Suba os containers usando Docker Compose:**
   - Após a construção da imagem, execute o comando abaixo para iniciar os containers do Spring Boot e PostgreSQL:
     ```bash
     docker-compose up
     ```

   - O container do Spring Boot ficará acessível em `http://localhost:8080`.
   - O PostgreSQL estará acessível em `localhost:5432`, com as credenciais configuradas no `docker-compose.yml`.

3. **Acesse a API via Swagger:**
   - A documentação interativa da API pode ser acessada através do Swagger em `http://localhost:8080/swagger-ui/`.

### 2. **Executando com Maven**

Alternativamente, você pode executar o projeto utilizando Maven, sem o uso de Docker. Certifique-se de ter o **Java 8 ou superior** e o **Maven** instalados.

#### Passos:
1. **Clone o repositório e navegue até o diretório do projeto:**
   ```bash
   git clone https://github.com/fhssouza/teste-stoom.git
   cd teste-stoom
   ```

2. **Compile e execute o projeto com Maven:**
   - Para compilar e rodar o aplicativo, execute o seguinte comando:
     ```bash
     mvn clean install
     mvn spring-boot:run
     ```

3. **Acesse a API via Swagger:**
   - Após o projeto iniciar, você pode acessar a documentação interativa da API em `http://localhost:8080/swagger-ui/`.

### 3. **Configurações no `application.properties`:**
No arquivo `application.properties` ou `application.yml` do Spring Boot, as configurações de conexão com o banco de dados são:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/store
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

## Testando a API com Postman

Uma coleção do Postman foi incluída para facilitar o teste dos endpoints.

1. Importe a collection disponível no repositório no Postman:
   - Arquivo: `Stoom-Teste.postman_collection`

2. Configure a variável `base_url` no Postman para o URL do servidor (por padrão: `http://localhost:8080`).
3. Utilize os endpoints da coleção para testar os recursos da API.

#### Endpoints disponíveis:
- **Brand Management**
- **Category Management**
- **Price Management**
- **Product Management**

Siga a sequência de requisições para realizar CRUD de marcas, categorias, preços e produtos.

## Endpoints da API

A API fornece os seguintes endpoints para gerenciar **marcas**, **categorias**, **produtos** e **preços**:

### **Brand Management** (Controle de Marcas)
- **GET /api/brands/**: Retorna todas as marcas.
- **POST /api/brands/**: Cria uma nova marca.
- **GET /api/brands/{id}**: Retorna uma marca pelo ID.
- **PUT /api/brands/{id}**: Atualiza uma marca existente.
- **DELETE /api/brands/{id}**: Deleta uma marca.
- **PATCH /api/brands/{id}/activate**: Ativa uma marca.
- **PATCH /api/brands/{id}/deactivate**: Desativa uma marca.

### **Category Management** (Controle de Categorias)
- **GET /api/categories**: Retorna todas as categorias.
- **POST /api/categories**: Cria uma nova categoria.
- **GET /api/categories/{id}**: Retorna uma categoria pelo ID.
- **PUT /api/categories/{id}**: Atualiza uma categoria existente.
- **DELETE /api/categories/{id}**: Deleta uma categoria.
- **PATCH /api/categories/{id}/activate**: Ativa uma categoria.
- **PATCH /api/categories/{id}/deactivate**: Desativa uma categoria.

### **Price Management** (Controle de Preços)
- **POST /api/products/{productId}/prices**: Adiciona um novo preço a um produto.

### **Product Management** (Controle de Produtos)
- **GET /api/products/**: Retorna todos os produtos.
- **POST /api/products/**: Cria um novo produto.
- **GET /api/products/{id}**: Retorna um produto pelo ID.
- **PUT /api/products/{id}**: Atualiza um produto existente.
- **DELETE /api/products/{id}**: Deleta um produto.
- **PATCH /api/products/{id}/activate**: Ativa um produto.
- **PATCH /api/products/{id}/deactivate**: Desativa um produto.
- **GET /api/products/brand/name/{brandName}**: Retorna produtos por nome da marca.
- **GET /api/products/category/name/{categoryName}**: Retorna produtos por nome da categoria.

## Swagger UI

A documentação interativa da API pode ser acessada através do Swagger UI:

- Acesse: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

Com o Swagger, você pode testar todos os endpoints da API diretamente na interface da web.

## Conclusão

Este projeto fornece uma API robusta para gerenciamento de produtos, categorias, marcas e preços, utilizando o Spring Boot para a implementação de uma arquitetura RESTful. Você pode executar o projeto usando Docker ou Maven, e consultar a documentação da API via Swagger.

---

Se você encontrar algum problema ou precisar de mais informações, sinta-se à vontade para abrir uma **issue** ou fazer perguntas!

