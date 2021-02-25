# Pact consumer example

## Use cases

### When a test doesn't exist for the @State

### When the test outcome doesn't match the expected output
example:

´
[ERROR]   ProducerContractVerifierTest.Provider should be pact compliant:32 Pact between spring-example-consumer (0.0.1-SNAPSHOT) and Provider - Upon a request
Failures:

1) Verifying a pact between spring-example-consumer and Provider - a request: has status code 200

   1.1) status: expected status of 200 but was 404

   1.2) body: $ Type mismatch: Expected List [{"id":1,"name":"name"},{"id":2,"name":"secondName"}] but received Map {"error":"Not Found","message":null,"path":"/person/","requestId":"ecb3fdb3-11","status":404,"timestamp":"2021-02-24T23:23:25.399+00:00"}

        [
          {
            "id": 1,
            "name": "name"
          },
          {
            "id": 2,
            "name": "secondName"
          }
        ]
        {
          "error": "Not Found",
          "message": null,
          "path": "/person/",
          "requestId": "ecb3fdb3-11",
          "status": 404,
          "timestamp": "2021-02-24T23:23:25.399+00:00"
        }
´
