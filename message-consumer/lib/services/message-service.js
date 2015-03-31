/*
Service dealing with messages.
 */

var mongoose = require('mongoose');
var Message  = require('../models/message')
var config   = require('../config');

mongoose.connect(config.dbURL);

/*
Validates message.
    parameters:
        message - message to be validated
    return:
        true if ok; false otherwise.
 */
var validate = function(message)
{
    var errors = [];

    if (!message.userId            ) errors.push('userId required');
    if (!message.currencyFrom      ) errors.push('currencyFrom required');
    if (!message.currencyTo        ) errors.push('currencyTo required');
    if (!message.amountSell        ) errors.push('amountSell required');
    if (!message.amountBuy         ) errors.push('amountBuy required');
    if (!message.rate              ) errors.push('rate required');
    if (!message.timePlaced        ) errors.push('timePlaced required');
    if (!message.originatingCountry) errors.push('originatingCountry required');

    return errors;
}

/*
Saves message.
    parameters:
        message - message to be saved.
 */
module.exports.save = function(json_message, callback)
{
    if (!json_message)
    {
        callback('Invalid message.');
        return;
    }

    var errors = validate(json_message);

    if (errors.length != 0)
    {
        callback(errors);
        return;
    }

    var message = Message();

    message.userId             = json_message.userId;
    message.currencyFrom       = json_message.currencyFrom;
    message.currencyTo         = json_message.currencyTo;
    message.amountSell         = json_message.amountSell;
    message.amountBuy          = json_message.amountBuy;
    message.rate               = json_message.rate;
    message.timePlaced         = json_message.timePlaced;
    message.originatingCountry = json_message.originatingCountry;
    message.processed          = false;

    message.save(function(err)
    {
        err ? callback(err) : callback();
    });
}
