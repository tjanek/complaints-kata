package pl.tjanek.complaints.adapters.external;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;
import pl.tjanek.complaints.domain.CountryResolver;

import java.util.Optional;

@RequiredArgsConstructor
class IpApiRestCountryResolver implements CountryResolver {

    private static final String UNKNOWN_COUNTRY = "Unknown";
    private static final String SUCCESS_STATUS = "success";

    private final String url;
    private final RestTemplate restTemplate;

    IpApiRestCountryResolver(RestTemplate restTemplate, String ipApiUrl) {
        this.restTemplate = restTemplate;
        this.url = ipApiUrl;
    }

    public String resolveCountry(String ipAddress) {
        try {
            IpApiResponse response = restTemplate.getForObject(url + ipAddress, IpApiResponse.class);
            return Optional.ofNullable(response)
                    .filter(r -> SUCCESS_STATUS.equals(r.status()))
                    .map(IpApiResponse::country)
                    .filter(StringUtils::isNotBlank)
                    .orElse(UNKNOWN_COUNTRY);
        } catch (Exception e) {
            return UNKNOWN_COUNTRY;
        }
    }

}
