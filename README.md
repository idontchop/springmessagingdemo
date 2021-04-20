# Spring Messaging Demo

Before integrating messaging into an application I'm working on, I demo'd Spring's kafka capabilities in this class. Most examples are taken from
https://www.baeldung.com/spring-kafka

A few problems arose in deserializing the json messages. A Poison Pill could cause this demo to infinitely loop. This issue should be dealt with before production.
