package pt.goncalo.producer

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import java.lang.Exception

@SpringBootApplication
class ProducerApplication{
	@Bean
	fun initializer(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
		val initializer = ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator( ResourceDatabasePopulator( ClassPathResource("schema.sql")));

		return initializer;
	}
}

fun main(args: Array<String>) {
	runApplication<ProducerApplication>(*args)


}
