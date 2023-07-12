docker exec -it test-kf1 kafka-topics --create --topic test-topic --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
#docker exec -it test-kf1 kafka-topics --create --topic test-topic --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092,localhost:9093,localhost:9094

pause