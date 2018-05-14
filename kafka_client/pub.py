import json

from confluent_kafka import Producer

conf = {
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'console-consumer-75148',
}


p = Producer(**conf)

data = {"text": "hello", "hashtag": "k1"}
p.produce('user', json.dumps(data, indent=4,
                             ).encode('utf-8'))

p.flush()
