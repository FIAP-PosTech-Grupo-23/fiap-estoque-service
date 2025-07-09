# FIAP Estoque Service

Serviço de gerenciamento de estoque para o projeto FIAP.

## 🚀 Tecnologias

- Java 21
- Spring Boot 3.5.0
- PostgreSQL 15
- Docker & Docker Compose
- Maven
- Flyway (migrações de banco)
- SpringDoc OpenAPI (Swagger)

## 📋 Pré-requisitos

- Docker e Docker Compose instalados
- Java 21 (para desenvolvimento local)
- Maven 3.9+ (para desenvolvimento local)

## 🔧 Configuração e Execução

### Executando com Docker Compose

```bash
# Subir os serviços (aplicação + PostgreSQL)
docker-compose up -d

# Verificar logs
docker-compose logs -f estoque

# Parar os serviços
docker-compose down
```

### Executando em desenvolvimento local

```bash
# 1. Subir apenas o PostgreSQL
docker-compose up -d postgresql

# 2. Executar a aplicação com perfil de desenvolvimento
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 🌐 Acesso aos Serviços

- **Aplicação**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **PostgreSQL**: localhost:5433 (porta externa)

## 📊 Banco de Dados

### Credenciais (desenvolvimento)
- **Host**: localhost
- **Porta**: 5433
- **Database**: estoque
- **Usuário**: postgres
- **Senha**: postgres

### Migrações

As migrações são executadas automaticamente pelo Flyway na inicialização da aplicação.
Arquivos de migração estão em: `src/main/resources/db/migration/`

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura
mvn verify

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

## 🐳 Docker

### Build manual da imagem

```bash
# Build da imagem
docker build -t fiap-estoque-service .

# Executar container
docker run -p 8081:8081 fiap-estoque-service
```

## 🔗 Integração com outros serviços

Este serviço faz parte da rede `fiap-network` e pode se comunicar com outros serviços FIAP:

- **fiap-cliente-service**: Porta 8080
- **fiap-estoque-service**: Porta 8081

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/fiap/estoque_service/
│   │   └── EstoqueServiceApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── db/migration/
│           └── V1__Create_estoque_table.sql
└── test/
    └── java/com/fiap/estoque_service/
        └── EstoqueServiceApplicationTests.java
```

## 🔧 Variáveis de Ambiente

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `ESTOQUE_DATASOURCE_URL` | Host do PostgreSQL | localhost |
| `ESTOQUE_DATASOURCE_PORT` | Porta do PostgreSQL | 5433 |
| `ESTOQUE_DATASOURCE_USERNAME` | Usuário do banco | postgres |
| `ESTOQUE_DATASOURCE_PASSWORD` | Senha do banco | postgres |

## 📝 API Documentation

A documentação da API está disponível via Swagger UI em:
http://localhost:8081/swagger-ui.html

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request
