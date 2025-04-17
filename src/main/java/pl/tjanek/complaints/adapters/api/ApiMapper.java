package pl.tjanek.complaints.adapters.api;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import pl.tjanek.complaints.domain.Complaint;

import java.util.List;

@Mapper(componentModel = "spring")
interface ApiMapper {

    ComplaintResponse toResponse(Complaint complaint);
    List<ComplaintResponse> toResponseList(List<Complaint> complaint);

    default PageResponse<ComplaintResponse> toPageResponse(Page<Complaint> page) {
        return new PageResponse<>(
                toResponseList(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}
