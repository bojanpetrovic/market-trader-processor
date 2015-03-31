# market-trader-processor
This project has 3 subprojects:
1. message-consumer
2. message-processor
3. message-frontend

## message-consumer
This is simple REST service that saves messages.
It is implemented in Node.js because Node's high throughput.

All messages are stored in MongoDB, because of it's speed. In further development, there would be some MongoDB replication involved: one node dedicated only for writing, and all others would be read-only nodes.

AAA is simple one: communication over SSL, and token based authentication. In this example we are faking tokens. In further development there would be some external authentication system that would generate temporary tickets, and store them in Redis
store.

First, you have to install Node.js libraries by runing `npm install`.
After that, you can run application by typing `node lib/app.js`.

Endopint for sending messages is https://localhost:8000/messages.

## message-processor
This is simple task application that processes messages. It is implemented on Spring 4 stack.

After messages are processed, some statistics are sent to observer interested in messages. In this example we have two observers:
1. console observer, that will display data on console
2. rest observer, which will send data to rest endpoints (via HTTPS)

First, you have to build application by running `./gradlew build`. After that, you can run application by typing `java build/libs/message-processor-{version}.jar`.

## message-frontend
This is dashboard application for watching processed messages. It is implemented in Node.js and AngularJS.

In this example AAA is also simple one, like in message-consumer subproject. Web page requires users to be authenticated, which in this example is:
* username : username
* password : password

Communication between browser and server is implemented with sockets, so data is constantly pushed to browser client.

First, you have to install Node.js libraries by runing `npm install`.
After that, you can run application by typing `node lib/app.js`.

Url to dashboard view is https://localhost:8080.