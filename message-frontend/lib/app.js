var fs          = require("fs");
var https       = require('https');
var express     = require("express");
var app         = express();
var bodyParser  = require("body-parser");
var authService = require("./services/auth-service")

// Load configuration
var config = require("./config");
var PORT = config["port"];

// JSON parsing
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Set logging
var winston    = require("winston");
var logger = new (winston.Logger)({
    transports: [
        new (winston.transports.Console)({"timestamp":true})
    ]
});
logger.level = "info";


// Start server
//var server = app.listen(PORT, function()
//{
//    logger.info("Application started listening on port %d", PORT);
//});
var sslOptions = {
    key: fs.readFileSync('./ssl/server.key'),
    cert: fs.readFileSync('./ssl/server.crt'),
    ca: fs.readFileSync('./ssl/ca.crt'),
    requestCert: true,
    rejectUnauthorized: false
};

// Create server
var server = https.createServer(sslOptions,app).listen(PORT, function()
{
    logger.info("Application started listening on port %d", PORT);
});

// Create socket
var io = require("socket.io").listen(server);

/*
Authenticates calls from processor which sends new fresh data.
 */
app.use(function(req, res, next)
{
    // Authenticate only incoming reports from processor
    if (req.path === "/messages/info")
    {
        authService.verifyIncomingReports(req.headers["token"], function(err)
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
    }
    next();
});


/*
 Serves static files for view.
 */
app.use(express.static(__dirname + "/public"));


/*
Endpoint for getting messages from message processor.
 */
app.post("/messages/info", function(req, res)
{
    logger.info("Fetching data from message processor.");
    io.emit("info", req.body);
    res.end();
});


/*
Endpoint for log in of dashboard users.
 */
app.post("/login", function(req, res)
{
    var credentials = req.body;
    authService.login(credentials.username, credentials.password, function(err, token) {

        if (err)
        {
            logger.warn("Invalid credentials for user %s", credentials.username);
            res.statusCode = 403;
            res.end("Forbidden");
        }
        else
        {
            res.end(token)
        }
    })
})


/*
    Authenticates all socket communication.
 */
io.use(function(socket, next)
{
    authService.verifyUser(socket.handshake.query.token, function(err)
    {
        if (err !== undefined)
        {
            logger.warn("Unauthorized use of socket from %s.", socket.request.connection.remoteAddress);
            next("Invalid user.");
        }
        else
        {
            next();
        }
    });
});