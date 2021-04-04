./kafka-console-consumer \
     --bootstrap-server localhost:9092 \
     --topic order-enrich \
     --property print.key=true \
     --from-beginning

./kafka-console-producer \
     --broker-list localhost:9092 \
     --topic orders \
     --property "parse.key=true" \
     --property "key.separator=:"
something:{"something":"one","and":"two", "foo":"foo"}