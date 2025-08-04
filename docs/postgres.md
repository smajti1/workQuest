### upgrade major version

In this step pay attention in:
- command `docker compose up -d` may create postgres users/database
- pgdump file may have code to remove actual selected user/database

Dump database to file

dbname and username variable make be different see `.env` file

    docker compose exec postgres-db pg_dump --no-owner --dbname=workQuest --username=workQuest > pg_dump_$(date +'%F').sql

upgrade postgres version in `docker-compose.yml` file

    docker compose down
    docker volume rm workquest_postgres
    docker compose up --detach
    docker compose exec -T postgres-db psql --dbname=workQuest --username=workQuest < pg_dump.sql

Download pg_dump_2024-02-08.sql from server

    scp USERNAME@IP_ADDRESS:prod/workQuest/pg_dump_$(date +'%F').sql .

https://www.postgresql.org/docs/current/app-pgdump.html


Put data_to_server.sql into server

    scp data_to_server.sql USERNAME@IP_ADDRESS:prod/workQuest/

### connect to db

To connect use command `docker compose exec postgres-db psql --password --dbname=workQuest --username=workQuest`
with data from `.env` file
