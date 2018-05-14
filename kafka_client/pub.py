from confluent_kafka import Producer

conf = {
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'console-consumer-75148',
}


p = Producer(**conf)

for data in ['hi', 'world']:
    p.produce('user', data.encode('utf-8'))

p.flush()
