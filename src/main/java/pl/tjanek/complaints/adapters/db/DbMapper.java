package pl.tjanek.complaints.adapters.db;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.tjanek.complaints.domain.Complaint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
interface DbMapper {

    ComplaintJpaEntity toEntity(Complaint complaint);

    @InheritInverseConfiguration
    Complaint toDomain(ComplaintJpaEntity entity);
}