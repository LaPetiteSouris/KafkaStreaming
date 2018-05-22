# Stream-processing data service

Modeling a simple light way service with fan-out capacity to distributed
work load over Kafka. This is suitable for fast stream-processing of a large
amount of incoming data.

Inspired by this post: </br>

  https://medium.com/@stephane.maarek/how-to-use-apache-kafka-to-transform-a-batch-pipeline-into-a-real-time-one-831b48a6ad85

I made up a small simplistic architecture to stream data from REST API to other services thanks to Kafka for
fast stream-processing. The architecture proposed by the author of the article aboved.

Here the REST API is a simple ExpressJS API and the streaming job is also
a simple Java application which utilizes Kafka Stream

![screenshot](https://user-images.githubusercontent.com/6369285/40370279-32d63c88-5de0-11e8-9dc1-a5c64e942361.jpg)

## How does it work ?

* `POST /message`: Put `{content: "mycontent is ..."}` into the post message
and the content will be relayed to a kafka topic (default is `fwd`). User will get 200 with ACK of message reception.

* The message is later processed in near real time
thanks to Kafka and notably Kafka Stream app (written in Java) to process the messsage.
In this simple application, any message which contains bad keywords will be filtered out. The
filtered message is then streamed back to Kafka topics.
* Another application
using Kafka Sink with DB connection will be in charge of writing all filtered messages
back to database for analyzing purpose.

## How to try it ?
