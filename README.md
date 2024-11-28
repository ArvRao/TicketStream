Application built using event driven architecture with Kafka which publishes events and ensures state management and consistency.

---
### Event Driven Architecture
Software architecture pattern that revolves around the production, detection, and reaction to events in a system. An event in this context is any significant change in the state of the system, or an occurrence that may trigger some action or process.
The architecture typically consists of three main components:
1. Event Producers: These are components or services that generate events. For example, when a user places an order on an e-commerce site, an event is generated to signify the new order.
2. Event Channels: These are the communication paths that transport events from producers to consumers. These can be message brokers, queues, or streams (such as Kafka, RabbitMQ, or AWS SNS).
3. Event Consumers: These are services or components that listen to events and take action based on them. For example, an inventory management system could listen for new order events and update the stock levels accordingly.

This kind of architecture helps in:
1. Decoupling different components or services unlike traditional request-response architecture
2. Asynchronous communication between services as services can operate independently
3. Real-time event processing
4. Scalability of the overall application

## Project Setup
> Make sure to have docker installed on your machine
### Setup docker compose

`docker-compose up -d`

### Verify if docker containers are running

`docker ps`

### Connect to the Kafka container

`docker exec -it <container_name> /bin/bash`

### Inside the Kafka container, create a new topic

`kafka-topics --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1`

### Produce a test message to the topic

`kafka-console-producer --topic test-topic --bootstrap-server localhost:9092`


*Enter Control + C to quit sending messages to kafka container*

### Consume messages from the topic
`kafka-console-consumer --topic test-topic --from-beginning --bootstrap-server localhost:9092`

### Create Twilio Sendgrid account
Create API key and later add it to your project.
In Linux systems, use command in root path:

`export SENDGRID_API_KEY=<key value>`

Verify if API Key is correctly set:

`echo $SENDGRID_API_KEY`

---
### Helpful Commands:
1. Clean build for gradle

`./gradlew clean build`
