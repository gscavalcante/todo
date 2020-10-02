package br.com.gcavalcante.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class Application {

    fun main(args: Array<String>) = runApplication<Application>(*args)

    @Bean
    fun restTemplate() = RestTemplate(HttpComponentsClientHttpRequestFactory())
}
