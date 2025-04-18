package pl.tjanek.complaints.abilities

import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

interface HttpRequestAbility {

    default ResponseEntity<Map> httpGetRequest(String url) {
        request(url, HttpMethod.GET, null)
    }

    default ResponseEntity<Map> httpPostRequest(String url, Map body = null) {
        request(url, HttpMethod.POST, body)
    }

    default ResponseEntity<Map> httpPutRequest(String url, Map body = null) {
        request(url, HttpMethod.PUT, body)
    }

    ResponseEntity<Map> request(String url, HttpMethod method, Map body)

}