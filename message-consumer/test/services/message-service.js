var request = require('superagent');
var expect = require('expect.js');

var messageService = require('../../lib/services/message-service')
var Message = require('../../lib/models/message')

// Mock message
Message.prototype.save = function(callback)
{
    callback();
};

describe('Add message.', function()
{

  it('Should report if message is null.', function(done)
  {
    messageService.save(null, function(err)
    {
        expect(err).to.exist;
        expect(err).to.equal('Invalid message.');
        done();
    });
  });

  it('Should report if message does not have all required fields.', function(done)
  {
    messageService.save({userId:'1234'}, function(err)
    {
        expect(err).to.exist;
        done();
    });
  });

  it('Should be ok if message is ok.', function(done)
  {
    var jsonMessage =
    {
        "userId"             : "134256",
        "currencyFrom"       : "EUR",
        "currencyTo"         : "GBP",
        "amountSell"         : 1000,
        "amountBuy"          : 747.10,
        "rate"               : 0.7471,
        "timePlaced"         : "24-JAN-15 10:27:44",
        "originatingCountry" : "FR"
    }

    messageService.save(jsonMessage, function(err)
    {
        expect(err).to.be(undefined);
        done();
    });
  });


});