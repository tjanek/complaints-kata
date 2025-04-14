package pl.tjanek.complaints

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import pl.tjanek.complaints.abilities.HttpRequestAbility
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntegrationSpec extends Specification implements HttpRequestAbility {

    @Value('${local.server.port}')
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    ResponseEntity<Map> request(String url, HttpMethod method, Map body = null) {
        def httpHeaders = new HttpHeaders()
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json")
        httpHeaders.add(HttpHeaders.ACCEPT, "application/json")
        restTemplate.exchange(
                "http://localhost:$port$url",
                method,
                new HttpEntity<>(body, httpHeaders),
                Map)
    }

}
