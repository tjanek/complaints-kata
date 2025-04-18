package pl.tjanek.complaints.abilities

import org.springframework.http.ResponseEntity

trait ChangeComplaintAbility extends ComplaintsHttpRequestAbility {

    ResponseEntity<Map> changeComplaint(String complaintId, Map params) {
        httpPutRequest("${COMPLAINTS_V1_BASE_URL}/${complaintId}", params)
    }

}