package pl.tjanek.complaints.adapters.api;

record AddNewComplaintRequest(String productId, String content, String complainant) {
}
