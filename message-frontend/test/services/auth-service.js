var request = require("superagent");
var expect = require("expect.js");

var authService = require("../../lib/services/auth-service")


describe("Verify tokens for incoming reports.", function()
{

  it("Should report if token is not provided.", function(done)
  {
    authService.verifyIncomingReports(null, function(err)
    {
        expect(err).to.exist;
        expect(err).to.equal("Token not provided.");
        done();
    });
  });

  it("Should report if token is not valid.", function(done)
  {
    authService.verifyIncomingReports("12", function(err)
    {
        expect(err).to.exist;
        expect(err).to.equal("Invalid token.");
        done();
    });
  });

  it("Should not report anything if token is ok.", function(done)
  {
    authService.verifyIncomingReports("1", function(err)
    {
        expect(err).to.be(undefined);
        done();
    });
  });

});


describe("Verify log in operation for dashboard users.", function()
{
    it("Should report invalid credentials.", function(done)
    {
        authService.login("a", "b", function(err)
        {
            expect(err).to.exist;
            expect(err).to.equal("Invalid credentials.");
            done();
        });
    });

    it("Should return token for good credentials.", function(done)
    {
        authService.login("username", "password", function(err, data)
        {
            expect(err).to.be(null);
            expect(data).to.exist;
            expect(data).to.equal("2");
            done();
        });
    });

});


describe("Verify tokens for dashboard users.", function()
{

    it("Should report if token is not provided.", function(done)
    {
        authService.verifyUser(null, function(err)
        {
            expect(err).to.exist;
            expect(err).to.equal("Token not provided.");
            done();
        });
    });

    it("Should report if token is not valid.", function(done)
    {
        authService.verifyUser("12", function(err)
        {
            expect(err).to.exist;
            expect(err).to.equal("Invalid token.");
            done();
        });
    });

    it("Should not report anything if token is ok.", function(done)
    {
        authService.verifyUser("2", function(err)
        {
            expect(err).to.be(undefined);
            done();
        });
    });

});