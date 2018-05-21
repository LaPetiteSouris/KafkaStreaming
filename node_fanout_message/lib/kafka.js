'use strict'
const kafka = require('kafka-node')

function buildKafkaProducer() {
	const Producer = kafka.HighLevelProducer;
	const client = new kafka.Client("localhost:2181/", "myclient" , {
    sessionTimeout: 3,
    spinDelay: 100,
    retries: 2
  })
	client.on('ready', function () {
		console.log("client ready");
	})

	client.on('error', function () {
		console.log("client error");
	})

	const producer = new Producer(client);
	return new Promise(function (resolve, reject) {
		producer.on('ready', function () {
			resolve(producer);
		});
		producer.on('ready', function () {
			reject(producer);
		});
	})
}

exports.buildKafkaProducer = buildKafkaProducer
