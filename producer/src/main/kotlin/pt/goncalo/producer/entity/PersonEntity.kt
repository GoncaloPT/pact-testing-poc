package pt.goncalo.producer.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("person")
data class PersonEntity(@Id var id: Long?, var name: String)