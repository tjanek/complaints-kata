package pl.tjanek.complaints.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintsRepository complaintRepository;
    private final CountryResolver countryResolver;

    @Transactional
    public Complaint addComplaint(CreateComplaintCommand command) {
        String country = countryResolver.resolveCountry(command.ipAddress());
        return complaintRepository.findByProductIdAndComplainant(command.productId(), command.complainant())
                .map(this::increaseSubmissionCount)
                .orElseGet(() -> complaintRepository.save(
                        newComplaint(command, country)
                ));
    }

    private static Complaint newComplaint(CreateComplaintCommand command, String country) {
        return Complaint.builder()
                .productId(command.productId())
                .content(command.content())
                .complainant(command.complainant())
                .country(country)
                .creationDate(LocalDateTime.now())
                .submissionCount(1)
                .build();
    }

    private Complaint increaseSubmissionCount(Complaint complaint) {
        complaint.setSubmissionCount(complaint.getSubmissionCount() + 1);
        return complaintRepository.save(complaint);
    }

    @Transactional
    public Optional<Complaint> editComplaintContent(Long id, String newContent) {
        return complaintRepository.findById(id)
                .map(complaint -> changeComplaintContent(newContent, complaint));
    }

    private Complaint changeComplaintContent(String newContent, Complaint complaint) {
        complaint.setContent(newContent);
        return complaintRepository.save(complaint);
    }

    @Transactional(readOnly = true)
    public Page<Complaint> getAllComplaints(Pageable pageable) {
        return complaintRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

}
