# Spring Kafka Messaging Demo

Before integrating messaging into an application I'm working on, I demo'd Spring's kafka capabilities in this class. Most examples are taken from
https://www.baeldung.com/spring-kafka

Adding an event-driven architecture to an app with kafka is my current goal. With this demo, I was able to form an idea of how messaging should work. I've listed a few bullet points below that tripped me up.

A few problems arose in deserializing the json messages. A Poison Pill could cause this demo to infinitely loop. This issue should be dealt with before production.

* The message should be the class, whether it be user / media / like / post etc. Deserialized to json.
* Each class should have a topic.
* Each topic should have a KafkaListener and each listener should have a filter to check for a properly structured set of headers and message body.
* The message key should be the ID of the class. For example, a user's id in the database is the key. This keeps all messages read in order.
* The event type should be in the header of the message. For example: event-type: createUser. This separates the event type from the class. Existences of event-type should be checked in the filter, but switching for event types is done in the listener (if the service uses more than one event-type).
* The service will consume the messages in the topic whether or not it has a matching event type.
* Idempotent producers should be used for delivering class information to insure data integrity in kafka.
