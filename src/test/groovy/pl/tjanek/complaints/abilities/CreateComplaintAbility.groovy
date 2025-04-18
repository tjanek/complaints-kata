package pl.tjanek.complaints.abilities

import org.springframework.http.ResponseEntity

trait CreateComplaintAbility extends ComplaintsHttpRequestAbility {

    ResponseEntity<Map> createNewComplaint(Map params) {
        httpPostRequest(COMPLAINTS_V1_BASE_URL, params)
    }

}