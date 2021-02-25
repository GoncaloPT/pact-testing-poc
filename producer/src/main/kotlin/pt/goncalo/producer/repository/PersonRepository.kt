package pt.goncalo.producer.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import pt.goncalo.producer.entity.PersonEntity

@Repository
interface PersonRepository: ReactiveCrudRepository<PersonEntity, Long>