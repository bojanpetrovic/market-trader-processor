var fs         = require("fs");
var https      = require('https');
var express    = require("express");
var app        = express();
var bodyParser = require("body-parser");

var messageService = require("./services/message-service")
var authService    = require("./services/auth-service")

//Load configuration
var config = require("./config");
var PORT = config["port"];

// Set logging
var winston    = require("winston");
var logger = new (winston.Logger)({
    transports: [
        new (winston.transports.Console)({'timestamp':true})
    ]
});
logger.level = "info";

// Set JSON parser
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


/*
 * Authenticates every REST call.
 */
app.use(function(req, res, next)
{
    authService.verify(req.headers["token"], function(err)
    {
        if (err)
        {
            logger.warn("Unauthorized access from %s", req.ip);
            res.statusCode = 401;
            res.end("Unauthorized");
        }
        else
        {
            next();
        }
    })
});


/*
Endpoint for saving messages.
 */
app.post("/messages", function(req, res)
{
    messageService.save(req.body, function(err)
    {
        if (err)
        {
            logger.warn("Error while saving message %s. Error was %s.", JSON.stringify(req.body), err);
            res.send(err);
        }

        res.end("Message saved");
    });
});

var sslOptions = {
    key: fs.readFileSync('./ssl/server.key'),
    cert: fs.readFileSync('./ssl/server.crt'),
    ca: fs.readFileSync('./ssl/ca.crt'),
    requestCert: true,
    rejectUnauthorized: false
};

// Create server
https.createServer(sslOptions,app).listen(PORT, function()
{
    logger.info("Application started listening on port %d", PORT);
});