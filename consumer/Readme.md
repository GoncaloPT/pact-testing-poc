# PACT Consumer

This project is an API consumer aka client. The main purpose is to showcase how pact can be used to ensure the API it
relies on has the behaviour this client is relying on.

# Good to know
## Send to PACT Broker
`mvn pact:publish` publishes the static file hosted in the project, therefore, to upload a new version after some change
you have to `mvn clean test` first

## Consumer name
The consumer name is defined in the tests, with the consumer parameter of `@Pact`

##


