#! /bin/bash
echo "creating db named ... "$USER"_DB"
createdb -h localhost -p $PGPORT $USER"_DB"
pg_ctl status
