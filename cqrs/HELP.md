# Getting Started

* curl -sX POST localhost:8080/orders -d @body.json --header "Content-Type: application/json" | jq
* curl localhost:8080/orders/nacci | jq
* curl -sX PUT localhost:8080/orders/1 -d @body.json --header "Content-Type: application/json" | jq
* curl -X DELETE localhost:8080/orders/naccis | jq
* curl localhost:8080/orders | jq

