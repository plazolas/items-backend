#!/bin/bash

mvn clean package
docker build -t items-backend:1.0.0 .
docker run --name items-backend -p 8077:8077 items-backend:1.0.0 &

sleep 5

# Test 1 check app is active
OUT=$(curl -s http://localhost:8077)
echo $OUT
if [[ "$OUT" == *"_links"*  ]]; then
    echo "server is active"
  else
    echo "server is not running"
    exit
fi

OUT=$(curl --location 'http://localhost:8077/api/vi/person/account/token?username=appuser&password=W2e3r4T5@')

TOKEN=$(echo "$OUT" | jq -r '.token')

OUT=$(curl --location 'http://localhost:8077/api/vi/person/' --header "Authorization: Bearer $TOKEN")
USER=$(echo "$OUT" | jq '.[0]')
POSITION=$(echo "$USER" | jq -r '.position')

if [[ "$POSITION" == *"president"* ]]; then
    echo "user with position $POSITION found!"
  else
    echo "No users listed"
    exit
fi

docker stop items-backend
docker rm -f items-backend
docker rmi items-backend:1.0.0

echo "==> all tests passed <=="

echo "bye!"
