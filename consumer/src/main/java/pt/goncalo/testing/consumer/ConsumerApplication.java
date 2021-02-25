package pt.goncalo.testing.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsumerApplication.class);
        // prevent start of web server
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);


    }

    @Bean
    public CommandLineRunner fetchListDemoEntities() {
        System.out.println("on fetchListDemoEntities");
        return args -> {

            List<Demo> demos = WebClient.create("http://localhost:8080")
                    .get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(ApiClientException::new))
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(ApiClientException::new))
                    .bodyToMono(new ParameterizedTypeReference<List<Demo>>() {
                    })
                    .block();
            demos.stream().forEach(System.out::println);


        };
    }

    private static class ApiClientException extends IOException {
        public ApiClientException() {
        }

        public ApiClientException(String message) {
            super(message);
        }

        public ApiClientException(String message, Throwable cause) {
            super(message, cause);
        }

        public ApiClientException(Throwable cause) {
            super(cause);
        }
    }

    private static class Demo {
        private int id;
        private String name;

        public Demo() {
        }

        public Demo(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Demo demo = (Demo) o;

            if (id != demo.id) return false;
            return name.equals(demo.name);
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + name.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Demo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
