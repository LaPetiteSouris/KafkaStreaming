'use strict'

const errors = require('./errors.js')
const kafkaLib = require('../lib/kafka')
const kafkaProducerPromise = kafkaLib.buildKafkaProducer()

const ackMessage = (req, res, next) => {
	const payload = [{
		topic: 'fwd',
		messages: [req.body.content], // multi messages should be a array, single message can be just a string,
		key: 'theKey', // string or buffer, only needed when using keyed partitioner
		attributes: 1,
		timestamp: Date.now(), // <-- defaults to Date.now() (only available with kafka v0.10 and KafkaClient only)
	}]

	kafkaProducerPromise.then(producer => {
		producer.send(payload, function(error, data) {
			console.log('data is ' + data)
			if (error) {
				next(errors.newHttpError(500, 'internal server error'));
			} else {
				res.status(200).json({ status: 'message ack' });
			}
		})
	}).catch((err) => {
		console.log('error upond sending '+ err);
		next(errors.newHttpError(500, 'internal server error'));
	})
}

exports.ackMessage = ackMessage

exports.buggyRoute = (req, res, next) => {
	// Simulate a custom error
	next(errors.newHttpError(400, 'bad request'))
}
