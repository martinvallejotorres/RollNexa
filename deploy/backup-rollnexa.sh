#!/usr/bin/env sh
set -eu

cd /opt/rollnexa
set -a
. ./.env
set +a

backup_dir=/opt/rollnexa/backups
timestamp=$(date -u +%Y%m%dT%H%M%SZ)
mkdir -p "$backup_dir"

docker compose --env-file .env -f compose.prod.yml exec -T db \
  pg_dump -U "$POSTGRES_USER" -d "$POSTGRES_DB" \
  | gzip > "$backup_dir/rollnexa-$timestamp.sql.gz"

find "$backup_dir" -type f -name 'rollnexa-*.sql.gz' -mtime +14 -delete
