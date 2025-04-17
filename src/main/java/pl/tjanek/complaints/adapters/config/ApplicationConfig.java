package pl.tjanek.complaints.adapters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tjanek.complaints.domain.ComplaintService;
import pl.tjanek.complaints.domain.ComplaintsRepository;
import pl.tjanek.complaints.domain.CountryResolver;

@Configuration(proxyBeanMethods = false)
class ApplicationConfig {

    @Bean
    ComplaintService complaintService(ComplaintsRepository complaintsRepository,
                                      CountryResolver countryResolver) {
        return new ComplaintService(complaintsRepository, countryResolver);
    }

}
