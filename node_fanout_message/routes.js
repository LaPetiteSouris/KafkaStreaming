'use strict';

let router = require('express').Router();

// Middleware
let middleware = require('./controllers/middleware');
router.use(middleware.doSomethingInteresting);

// Message
let message = require('./controllers/message');
router.post('/message', message.ackMessage);

// Error Handling
let errors = require('./controllers/errors');
router.use(errors.errorHandler);

// Request was not picked up by a route, send 404
router.use(errors.nullRoute);

// Export the router
module.exports = router;
