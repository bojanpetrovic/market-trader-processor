/*
 Service dealing with AAA.
 */


/*
 Verifies if client is allowed to call REST service for incoming reports.
 */
module.exports.verifyIncomingReports = function(token, callback)
{
    // Calling some external AAA system.
    // Here we are faking it.
    if (!token)
        callback('Token not provided.');

    else if (token != '1')
        callback('Invalid token.');

    else
        callback();
};


/*
 Performs log in for user credentials. If credentials are ok, temporary token is provided.
 */
module.exports.login = function(username, password, callback)
{
    // This should call some external AAA system for publishing temporary AAA tokens.
    if (username === "username" && password === "password")
        callback(null, "2");
    else
        callback("Invalid credentials.");
};


/*
    Performs dashboard user verification.
 */
module.exports.verifyUser = function(token, callback)
{
    if (!token)
        callback("Token not provided.");

    else if (token != "2")
        callback("Invalid token.");

    else
        callback();
};