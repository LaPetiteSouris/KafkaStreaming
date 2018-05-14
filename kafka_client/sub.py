import sys
from confluent_kafka import Consumer, KafkaException, KafkaError

conf = {
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'console-consumer-75148',

}
c = Consumer(**conf)
c.subscribe(['user'])

while True:
    msg = c.poll(timeout=1.0)
    if msg is None:
        continue
    if msg.error():
        # Error or event
        if msg.error().code() == KafkaError._PARTITION_EOF:
            # End of partition event
            sys.stderr.write('%% %s [%d] reached end at offset %d\n' %
                             (msg.topic(), msg.partition(), msg.offset()))
        elif msg.error():
            # Error
            raise KafkaException(msg.error())
    else:
        # Proper message
        sys.stderr.write('%% %s [%d] at offset %d with key %s:\n' %
                         (msg.topic(), msg.partition(), msg.offset(),
                          str(msg.key())))
        print(msg.value())
