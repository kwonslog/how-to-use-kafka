docker exec -it test-kf1 kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic test-topic --time -1
pause