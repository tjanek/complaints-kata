package pl.tjanek.complaints.adapters.api;

import java.time.LocalDateTime;

record ComplaintResponse(Long id,
                         String productId,
                         String content,
                         LocalDateTime creationDate,
                         String complainant,
                         String country,
                         Integer submissionCount) {
}