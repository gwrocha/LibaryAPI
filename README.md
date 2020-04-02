# LibaryAPI

Uma simples API implementando o funcionamento do Spring Security utilizando uma autenticação via token e fazendo a validação das autorizações.

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
Para parar o banco pode ser usado um dos seguintes comandos:
```
docker stack rm libary_api
```
_Obs: Esse arquivo também utiliza a mesma sintaxe utilizada no comando `docker-compose`. Porém é necessário instalar o [Docker Compose](https://docs.docker.com/compose/)_
