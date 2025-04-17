package pl.tjanek.complaints

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import pl.tjanek.complaints.abilities.HttpRequestAbility
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntegrationSpec extends Specification implements HttpRequestAbility {

    @Shared
    WireMockServer wiremockServer = new WireMockServer(9090)

    @Value('${local.server.port}')
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    def setupSpec() {
        wiremockServer.start()
    }

    def cleanupSpec() {
        wiremockServer.stop()
    }

    def stubForIpApiResponse(String ipAddress, String country) {
        wiremockServer.stubFor(
            get(urlEqualTo("/${ipAddress}"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""
                        {
                            "status": "success",
                            "country": "${country}",
                            "countryCode": "US",
                            "region": "CA",
                            "regionName": "California",
                            "city": "San Francisco",
                            "zip": "94105",
                            "lat": 37.7852,
                            "lon": -122.3874,
                            "timezone": "America/Los_Angeles",
                            "isp": "Example ISP",
                            "org": "Example Org",
                            "as": "AS123 Example AS",
                            "query": "${ipAddress}"
                        }
                    """)
                )
        )
    }

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
