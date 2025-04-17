package pl.tjanek.complaints.controller


import org.springframework.http.HttpStatus
import pl.tjanek.complaints.BaseIntegrationSpec
import spock.lang.Stepwise

@Stepwise
class ComplaintControllerSpec extends BaseIntegrationSpec {

    def setup() {
        stubForIpApiResponse("127.0.0.1", "Poland")
        stubForIpApiResponse("0.0.0.0", "Poland")
    }

    def "should add a new complaint"() {
        given:
        def request = [
                productId   : "product123",
                content     : "Complaint content",
                complainant : "some.mail@example.com"
        ]

        when:
        def response = httpPostRequest("/api/v1/complaints", request)

        then:
        response.statusCode == HttpStatus.CREATED
        response.body.productId == "product123"
        response.body.content == "Complaint content"
        response.body.complainant == "some.mail@example.com"
        response.body.submissionCount == 1
        response.body.country == "Poland"
        response.body.creationDate != null
        response.body.id != null
    }

    def "should increment submission count for duplicate complaint"() {
        given:
        def request = [
                productId   : "product123",
                content     : "This is a different content",
                complainant : "some.mail@example.com"
        ]

        when:
        def response = httpPostRequest("/api/v1/complaints", request)

        then:
        response.statusCode == HttpStatus.CREATED
        response.body.productId == "product123"
        response.body.content == "Complaint content"
        response.body.complainant == "some.mail@example.com"
        response.body.submissionCount == 2
    }

    def "should edit complaint content"() {
        given:
        def getAllResponse = httpGetRequest("/api/v1/complaints")
        def complaintId = getAllResponse.body.content[0].id
        def request = [
                content: "Updated content for the complaint"
        ]

        when:
        def response = httpPutRequest("/api/v1/complaints/${complaintId}", request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.id == complaintId
        response.body.content == "Updated content for the complaint"
        response.body.submissionCount == 2 // Unchanged
    }

    def "should get all complaints with pagination"() {
        when:
        def response = httpGetRequest("/api/v1/complaints?page=0&size=10")

        then:
        response.statusCode == HttpStatus.OK
        response.body.content.size() >= 1
        response.body.pageNumber == 0
        response.body.pageSize == 10
        response.body.totalElements >= 1
        response.body.totalPages >= 1
    }

    def "should get complaint by id"() {
        given:
        def getAllResponse = httpGetRequest("/api/v1/complaints")
        def complaintId = getAllResponse.body.content[0].id

        when:
        def response = httpGetRequest("/api/v1/complaints/${complaintId}")

        then:
        response.statusCode == HttpStatus.OK
        response.body.id == complaintId
    }

    def "should return 404 for non-existent complaint"() {
        when:
        def response = httpGetRequest("/api/v1/complaints/999999")

        then:
        response.statusCode == HttpStatus.NOT_FOUND
    }

}
