package pl.tjanek.complaints.adapters.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tjanek.complaints.domain.Complaint;
import pl.tjanek.complaints.domain.ComplaintsRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
class ComplaintsRepositoryJpaDbRepository implements ComplaintsRepository {

    private final ComplaintsJpaRepository repository;
    private final DbMapper dbMapper;

    @Override
    public Complaint save(Complaint complaint) {
        ComplaintJpaEntity entity = dbMapper.toEntity(complaint);
        return dbMapper.toDomain(repository.save(entity));
    }

    @Override
    public Page<Complaint> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(dbMapper::toDomain);
    }

    @Override
    public Optional<Complaint> findById(Long id) {
        return repository.findById(id)
                .map(dbMapper::toDomain);
    }

    @Override
    public Optional<Complaint> findByProductIdAndComplainant(String productId, String complainant) {
        return repository.findByProductIdAndComplainant(productId, complainant)
                .map(dbMapper::toDomain);
    }

}

@Repository
interface ComplaintsJpaRepository extends JpaRepository<ComplaintJpaEntity, Long> {
    Optional<ComplaintJpaEntity> findByProductIdAndComplainant(String productId, String complainant);
}

@Entity
@Table(name = "complaints", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "complainant"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ComplaintJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(nullable = false)
    private String content;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String complainant;

    @Column(nullable = false)
    private String country;

    @Column(name = "submission_count", nullable = false)
    private Integer submissionCount;

    @Column(name = "version")
    @Version
    private Integer version;

}
