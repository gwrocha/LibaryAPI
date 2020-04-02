# LibaryAPI

Uma simples API implementando o funcionamento do Spring Security utilizando uma autenticação via token e fazendo a validação das autorizações.
A API tem apenas uma listagem de livros e de autores

## Tecnologias utilizadas:
- Sping Boot: 2.2.6.RELEASE
	- Security;
	- Data-JPA;
	- Devtools;
- Docke:r API 1.25+
	- Postgres: 12
- JWT: 0.11.1

## Banco de dados
Esse projeto está utilizando o banco de dados Postgres. Na pasta `src/main/resources` existe um arquivo que inicia um container docker já com as configurações de acesso ao banco que a aplicação utiliza. Para subir o container usando o arquivo `stack_db.yaml` basta executar o seguinte comando dentro da pasta ressouces:
```
docker stack deploy -c stack_db.yaml libary_api
```
Para parar o container com banco de dados pode ser usado um dos seguintes comandos:
```
docker stack rm libary_api
```

_Obs: Esse arquivo também utiliza a mesma sintaxe utilizada no comando `docker-compose`. Porém é necessário instalar o [Docker Compose](https://docs.docker.com/compose/)_

## Inciando a Aplicação
Após o iniciar o banco de dados, para iniciar a aplicação, basta executar o seguinte comando:
```
./mvnw spring-boot:run
```

Ao iniciar em um banco de dados a primeira vez serão cadastrados dois usuários:
|Username|Password|Role|
|---|---|---|
|admin|admin|ADMIN|
|gwrocha|123456|USER|

## Utilizando a Aplicação
Para consumir os endpoints da aplicação é necessário obter um token de acesso primeiro. Para isso basta fazer um POST no endpoint {{host}}:8080/login passando os dados de acesso no corpo da requisição como `form-data`. Exemplo:
```
curl -X POST -F 'username=admin' -F 'password=admin' http://{{host}}:8080/login 
```

Com isso o sistema responderá com o token:
```
{"type":"Baerer","token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU4NTgyNDMxOCwiZXhwIjoxNTg1OTEwNzE4fQ.uerZx0ihbZpzVnJmlwR0QtqZmH-Yp_8dt7Faw4ckOP2lD2e3vrCkz1ZUTwxyFAEV_KZw6LV3rK5s5tux6wNILQ"}
```

Com esse token é possível ter acesso, de acordo com a role do usuário, aos dados da aplicação. O token deve ser passado no header da requisição, sendo a chave **Authorization** e p valor **Baerer {{token}}**. Exemplo:
```
curl -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU4NTgyNDMxOCwiZXhwIjoxNTg1OTEwNzE4fQ.uerZx0ihbZpzVnJmlwR0QtqZmH-Yp_8dt7Faw4ckOP2lD2e3vrCkz1ZUTwxyFAEV_KZw6LV3rK5s5tux6wNILQ' http://tars:8080/books 
```
