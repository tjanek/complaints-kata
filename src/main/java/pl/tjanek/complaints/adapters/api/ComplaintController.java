package pl.tjanek.complaints.adapters.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tjanek.complaints.domain.Complaint;
import pl.tjanek.complaints.domain.ComplaintService;
import pl.tjanek.complaints.domain.CreateComplaintCommand;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
class ComplaintController {

    private final ComplaintService complaintService;
    private final ApiMapper apiMapper;

    @PostMapping
    ResponseEntity<ComplaintResponse> addComplaint(@RequestBody AddNewComplaintRequest request, HttpServletRequest httpRequest) {
        String ipAddress = getClientIpAddress(httpRequest);
        Complaint complaint = complaintService.addComplaint(
                new CreateComplaintCommand(
                        request.productId(),
                        request.content(),
                        request.complainant(),
                        ipAddress
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiMapper.toResponse(complaint));
    }

    @PutMapping("/{id}")
    ResponseEntity<ComplaintResponse> editComplaintContent(@PathVariable Long id, @RequestBody UpdateComplaintContentRequest request) {
        return complaintService.editComplaintContent(id, request.content())
                .map(apiMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<PageResponse<ComplaintResponse>> getAllComplaints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Complaint> complaints = complaintService.getAllComplaints(pageable);
        return ResponseEntity.ok(apiMapper.toPageResponse(complaints));
    }

    @GetMapping("/{id}")
    ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id)
                .map(apiMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

}
