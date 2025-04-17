package pl.tjanek.complaints.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    private Long id;
    private String productId;
    private String content;
    private LocalDateTime creationDate;
    private String complainant;
    private String country;
    private Integer submissionCount;
    private Integer version;

}
