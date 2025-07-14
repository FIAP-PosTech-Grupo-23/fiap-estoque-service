# FIAP Estoque Service

Microserviço de gerenciamento de estoque implementado com **Clean Architecture**.

## 🚀 Tecnologias

- **Java 21** + **Spring Boot 3.5.0**
- **PostgreSQL 15** com **Flyway**
- **Clean Architecture** (Domain, Use Cases, Gateways)
- **MapStruct** para mapeamentos
- **JaCoCo** para cobertura de código (81%)
- **Docker Compose** para ambiente local

## ⚡ Como Rodar

### 🐳 Com Docker (Recomendado)

```bash
# Subir aplicação + PostgreSQL
docker-compose up -d

# Ver logs
docker-compose logs -f estoque
```

### 💻 Desenvolvimento Local

```bash
# 1. Subir apenas PostgreSQL
docker-compose up -d postgresql

# 2. Rodar aplicação
./mvnw spring-boot:run
```

## 🌐 Acessos

- **API**: http://localhost:8081
- **Swagger**: http://localhost:8081/swagger-ui.html
- **PostgreSQL**: localhost:5433 (postgres/postgres)

## 🧪 Testes

```bash
# Rodar testes
./mvnw test

# Gerar relatório de cobertura
./mvnw verify

# Ver cobertura (81%)
open target/site/jacoco/index.html
```

## � Funcionalidades

- **Criar estoque** para produtos
- **Consultar disponibilidade** 
- **Baixa em lote** com validação
- **Clean Architecture** com camadas bem definidas
- **Testes unitários** focados em lógica de negócio
