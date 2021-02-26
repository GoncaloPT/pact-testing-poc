# AngularConsumerJest

just a demonstration on how Pact can also be used from an angular consumer

## Tools
This project took in consideration the current (dsmp.next) specification that uses angular 10 and jest

## Run pact consumer tests
`npm test` can be used since it was modified to include `npm test:pact`
This doesn't push the pact file to the broker

## push pact file to pact-broker
Pact files are generated to pacts/ folder.
pact cli can be used to submit those pacts, or in alternative:
Use `npm run publish:pact` to submit your pact file to the broker. See `/pact-publisher` for more details.


## references
https://medium.com/@dany.marques/how-to-set-up-pact-tests-with-angular-jest-ae157f272428


