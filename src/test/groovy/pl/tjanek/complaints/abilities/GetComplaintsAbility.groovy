package pl.tjanek.complaints.abilities

import org.springframework.http.ResponseEntity

trait GetComplaintsAbility extends ComplaintsHttpRequestAbility {

    ResponseEntity<Map> getComplaints() {
        httpGetRequest(COMPLAINTS_V1_BASE_URL)
    }

    ResponseEntity<Map> getComplaint(String complaintId) {
        httpGetRequest("${COMPLAINTS_V1_BASE_URL}/${complaintId}")
    }

}