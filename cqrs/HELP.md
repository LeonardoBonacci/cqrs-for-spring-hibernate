# Getting Started

* curl -sX POST localhost:8081/orders -d @body.json --header "Content-Type: application/json" | jq
* curl localhost:8081/orders/foo | jq
* curl -sX PUT localhost:8081/orders/1 -d @body.json --header "Content-Type: application/json" | jq
* curl -X DELETE localhost:8081/orders/foo | jq
* curl localhost:8081/orders | jq

