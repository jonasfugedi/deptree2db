#!/usr/bin/env bash

DB_PATH=/tmp/maven-inventory.sqlite

GIT_HASH=$(git rev-parse HEAD)
GIT_BRANCH=$(git branch --show-current)
GIT_REPO=$(basename "$PWD")
GIT_URL=$(git config --get remote.origin.url)
CHECK_TIME=$(date --iso-8601=seconds)

sqlite3 $DB_PATH "CREATE TABLE IF NOT EXISTS inventory (entry INTEGER PRIMARY KEY, timestamp TEXT, repo TEXT, url TEXT, branch TEXT, hash TEXT);"
sqlite3 $DB_PATH "CREATE TABLE IF NOT EXISTS deps (entry INTEGER, 'group' text, artifact text, version text, 'dep_group' TEXT, dep_artifact TEXT, dep_version TEXT, UNIQUE(entry, 'group', artifact, version, dep_group, dep_artifact, dep_version) ON CONFLICT IGNORE);"

ENTRY_ID=$(sqlite3 $DB_PATH "INSERT INTO inventory (timestamp, repo, url, branch, hash) VALUES('$CHECK_TIME', '$GIT_REPO', '$GIT_URL', '$GIT_BRANCH', '$GIT_HASH') RETURNING entry;")

echo "Inventory entry=$ENTRY_ID"

rm -f $PWD/dep_graph.dot

# -Dincludes=com.tajjm.*
mvn dependency:tree -DoutputType=dot -DoutputFile=$PWD/dep_graph.dot -DappendOutput=true

SCRIPT_PATH=$(readlink -f ${BASH_SOURCE[0]})
SCRIPT_DIR=$(dirname ${SCRIPT_PATH})

java -jar $SCRIPT_DIR/target/deptree2db-1.0-SNAPSHOT.jar $ENTRY_ID $PWD/dep_graph.dot > deps.csv

sqlite3 -csv $DB_PATH ".import $PWD/deps.csv deps"