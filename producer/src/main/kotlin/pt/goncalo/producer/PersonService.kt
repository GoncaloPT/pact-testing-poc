package pt.goncalo.producer

import org.springframework.stereotype.Component
import pt.goncalo.producer.entity.PersonEntity
import pt.goncalo.producer.repository.PersonRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Stream
import kotlin.streams.toList


@Component
class PersonService(private val repository: PersonRepository) {


    /**
     * Calls database and transforms from Entity object to DTO object
     */
    fun list(): Flux<Person> {
        return repository.findAll().map { person -> Person(person?.id, person.name) }
    }

    fun createDummyData(): Flux<Person> {

        val entities: Stream<PersonEntity> = Stream.of(PersonEntity(null,"enough"))
        return repository
            .saveAll<PersonEntity> (Flux.fromIterable(entities.toList()))
            .map { personEntity -> Person(personEntity.id,personEntity.name)
            }

        /*return repository
            .saveAll<PersonEntity> (Flux.from { sequence<PersonEntity> { yield( PersonEntity(null,"enough")) } })
            .map { personEntity -> Person(personEntity.id,personEntity.name)
            }*/
    }


    fun createDummySingle(): Mono<Person> = repository
        .save<PersonEntity>(PersonEntity(null, "blabla"))
        .map { personEntity -> Person(personEntity.id,personEntity.name) }

}
