package pt.goncalo.testing.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.apache.tika.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pt.goncalo.testing.api.dto.Person;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "Provider", port = "1234")
class ConsumerApplicationTest {

    // This sets up a mock server that pretends to be our provider
    /*@Rule
    public PactProviderRule provider = new PactProviderRule("Provider", "localhost", 8080, this);*/


    /**
     * Only if all are pact tests
     *
     * @param mockServer
     */
    @BeforeEach
    public void setUp(MockServer mockServer) {
        assertThat(mockServer, is(notNullValue()));
    }

    /**
     * Uses the POJO definition {@link Person} to build the expected response
     *
     * @param builder
     * @return
     * @throws JsonProcessingException
     */
    @Pact(provider = "Provider", consumer = "spring-example-consumer")
    public RequestResponsePact peopleExist(PactDslWithProvider builder) {
        DslPart body = PactDslJsonArray.arrayMinLike(1).id().stringType("name");

        return builder
                .given("person has to have name and numeric id")
                .uponReceiving("a request")
                .path("/person/")
                .method("GET")
                .willRespondWith().body(body)
                //this forces the server to have the same exact response .body(objectMapper.writeValueAsString(peopleResponse))
                .status(200)
                .toPact();
    }



    /**
     * Builder for patch where /people/ API returns 404
     *
     * @param builder
     * @return
     */
    @Pact(provider = "Provider", consumer = "spring-example-consumer")
    public RequestResponsePact personNotFound(PactDslWithProvider builder) {
        return builder
                .given("No person exist, empty collections is return")
                .uponReceiving("retrieving person data")
                .path("/person/")
                .method("GET")
                .willRespondWith().body("[]")
                .status(200)
                .toPact();

    }


    /**
     * Ensures that everyone must have a name and id fits in an Integer
     *
     * @param mockServer
     * @throws IOException
     */
    @Test
    @PactTestFor(pactMethod = "peopleExist")
    void everyoneHasName(MockServer mockServer) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity entity = Request.Get(mockServer.getUrl() + "/person/")
                .execute()
                .returnResponse()
                .getEntity();

        Person[] people = objectMapper.readValue(EntityUtils.toString(entity), Person[].class);
        for (Person p : people) {
            assertThat(p.getName(), is(notNullValue()));
            assertThat(p.getId(), isA(Integer.class));
        }
    }


    /**
     * Empty collections responses are returned instead of 404
     *
     * @param mockServer
     * @throws IOException
     */
    @Test
    @PactTestFor(pactMethod = "personNotFound")
    void testEmptyGetPeople(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/person/").execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
        assertThat(IOUtils.toString(httpResponse.getEntity().getContent()), is(equalTo("[]")));
    }


}
