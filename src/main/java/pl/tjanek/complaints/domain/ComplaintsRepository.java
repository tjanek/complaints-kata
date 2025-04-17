package pl.tjanek.complaints.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintsRepository {

    Complaint save(Complaint complaint);
    Page<Complaint> findAll(Pageable pageable);
    Optional<Complaint> findById(Long id);
    Optional<Complaint> findByProductIdAndComplainant(String productId, String complainant);

}
