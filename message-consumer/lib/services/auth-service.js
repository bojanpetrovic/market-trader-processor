/*
 Service dealing with AAA.
 */

/*
Verifies if client is allowed to call REST service for given token.
 */
module.exports.verify = function(token, callback)
{
    // Here we should call some external AAA system, but for this example
    // we are faking call.
    if (!token)
        callback('Token not provided.');

    else if (token != '1')
        callback('Invalid token.');

    else
        callback();
}