package pl.tjanek.complaints.adapters.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.tjanek.complaints.domain.CountryResolver;

@Configuration(proxyBeanMethods = false)
class CountryResolverConfig {

    @Bean
    CountryResolver countryResolver(RestTemplate countryResolverRestTemplate, @Value("${ip-api.url}") String ipApiUrl) {
        return new IpApiRestCountryResolver(countryResolverRestTemplate, ipApiUrl);
    }

    @Bean
    public RestTemplate countryResolverRestTemplate() {
        return new RestTemplate();
    }

}
