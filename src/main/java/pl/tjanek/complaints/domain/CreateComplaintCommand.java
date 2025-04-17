package pl.tjanek.complaints.domain;

public record CreateComplaintCommand(String productId, String content, String complainant, String ipAddress) {
}