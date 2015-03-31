var request = require('superagent');
var expect = require('expect.js');

var authService = require('../../lib/services/auth-service')

describe('Verify tokens.', function()
{

  it('Should report if token is not provided.', function(done)
  {
    authService.verify(null, function(err)
    {
        expect(err).to.exist;
        expect(err).to.equal('Token not provided.');
        done();
    });
  });

  it('Should report if token is not valid.', function(done)
  {
    authService.verify('12', function(err)
    {
        expect(err).to.exist;
        expect(err).to.equal('Invalid token.');
        done();
    });
  });

  it('Should not report anything if token is ok.', function(done)
  {
    authService.verify('1', function(err)
    {
        expect(err).to.be(undefined);
        done();
    });
  });

});