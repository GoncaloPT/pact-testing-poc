package pt.goncalo.producer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Stream
import kotlin.streams.toList


@RestController
@RequestMapping("person")
class PersonController(private val service: PersonService) {


    @GetMapping("/")
    fun list(): Flux<Person> = service.list()

    @GetMapping("/create")
    fun createDummy(): Mono<Person> = service.createDummySingle()

    @PostMapping("/")
    fun create(): Flux<Person> = service.createDummyData()


}

data class Person(val id: Long?, val name: String);