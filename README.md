# Concessionaria BYD

Projeto da unidade 2 da disciplina Programacao Web.

Tema: concessionaria de carros BYD.

## Tecnologias

- Java 21
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Spring Security
- Maven

## Funcionalidades

- Catalogo de carros BYD
- Tela de detalhes por ID
- Carrinho em sessao HTTP
- Finalizacao de compra com escolha entre Pix e financiamento
- CRUD administrativo
- Soft delete com `isDeleted`
- Restauracao de registros
- Validacao no servidor com DTO/Form Object
- Login com Spring Security
- Controle de acesso por roles
- Layout com fragments Thymeleaf
- Paginas de erro customizadas

## Usuarios de teste

Administrador:

```text
usuario: admin
senha: admin123
```

Visitante:

```text
usuario: visitante
senha: visitante123
```

## Requisitos para rodar

Antes de executar, instale:

- Java 21
- Maven
- PostgreSQL

Confirme as versoes:

```bash
java -version
mvn -version
```

## Banco de dados

Crie um banco PostgreSQL chamado:

```text
concessionaria_byd
```

Exemplo usando `psql`:

```bash
psql -U postgres
CREATE DATABASE concessionaria_byd;
\q
```

O projeto cria/atualiza as tabelas automaticamente porque usa:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Ao iniciar pela primeira vez, o sistema cadastra automaticamente os 5 modelos:

- BYD Dolphin
- BYD Dolphin Mini
- BYD Shark
- BYD King
- BYD Seal

## Variaveis de ambiente

O projeto le as seguintes variaveis:

```env
DATABASE_URL=jdbc:postgresql://localhost:5432/concessionaria_byd
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres
```

Se o PostgreSQL do seu colega usar outra senha, ele deve trocar apenas `DATABASE_PASSWORD`.

## Rodar no PowerShell

Entre na pasta do projeto:

```powershell
cd "C:\Users\Matheus\Documents\PW prova und 2\concessionaria-byd"
```

Defina as variaveis de ambiente:

```powershell
$env:DATABASE_URL="jdbc:postgresql://localhost:5432/concessionaria_byd"
$env:DATABASE_USERNAME="postgres"
$env:DATABASE_PASSWORD="postgres"
```

Execute:

```powershell
mvn spring-boot:run
```

Acesse:

```text
http://localhost:8080
```

## Rodar pelo JAR

Gere o arquivo `.jar`:

```powershell
mvn -DskipTests package
```

Execute:

```powershell
java -jar target\byd-0.0.1-SNAPSHOT.jar
```

## Rodar no Linux/WSL

```bash
export DATABASE_URL="jdbc:postgresql://localhost:5432/concessionaria_byd"
export DATABASE_USERNAME="postgres"
export DATABASE_PASSWORD="postgres"
mvn spring-boot:run
```

## Dockerfile

O projeto possui `Dockerfile` para deploy em ambiente como Render.

As variaveis de ambiente no servico de deploy devem ser:

```env
DATABASE_URL=jdbc:postgresql://HOST:PORTA/NOME_DO_BANCO
DATABASE_USERNAME=USUARIO_DO_BANCO
DATABASE_PASSWORD=SENHA_DO_BANCO
```
