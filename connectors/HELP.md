curl localhost:8083/connectors | jq

curl -sX POST http://localhost:8083/connectors -d @es-sink.json --header "Content-Type: application/json" | jq

curl -X DELETE -H "Content-type: application/json" http://localhost:8083/connectors/es-sink | jq
