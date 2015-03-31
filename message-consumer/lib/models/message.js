var mongoose = require('mongoose');
var Schema   = mongoose.Schema;

/*
REST Message model.
 */
var MessageSchema = new Schema(
{
    userId             : String,
    currencyFrom       : String,
    currencyTo         : String,
    amountSell         : Number,
    amountBuy          : Number,
    rate               : Number,
    timePlaced         : Date,
    originatingCountry : String,
    processed          : Boolean
});

module.exports = mongoose.model('Message', MessageSchema);