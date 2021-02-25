package pt.goncalo.producer

import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactBroker
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URL
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext

import org.springframework.beans.factory.annotation.Autowired
import pt.goncalo.producer.entity.PersonEntity
import pt.goncalo.producer.repository.PersonRepository
import reactor.core.publisher.Flux
import java.util.stream.Stream
import kotlin.streams.toList


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("Provider")
@PactBroker
class ProducerContractVerifierTest() {
    @Autowired
    private val server: ReactiveWebServerApplicationContext? = null

    @Autowired
    private val personRepository: PersonRepository? = null;

    @BeforeEach
    fun before(context: PactVerificationContext) {
        context.target = HttpTestTarget.fromUrl(URL("http://localhost:" + server?.webServer?.port))
        //database cleanup
        personRepository?.deleteAll();
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider::class)
    fun `Provider should be pact compliant`(context: PactVerificationContext) {
        context.verifyInteraction();
    }
    @State("No person exist, empty collections is return")
    fun `When no people exists`() {
        // Nothing todo, the database is empty
    }


    @State("person has to have name and numeric id")
    fun `Person list must have names`() {
        val entities: Stream<PersonEntity> =
            Stream.of(
                PersonEntity(null, "enough"),
                PersonEntity(null, "second"),
                PersonEntity(null, "third"),
                PersonEntity(null, "forth"),

            )
        personRepository!!.saveAll(Flux.fromIterable(entities.toList())).subscribe()

    }

}
