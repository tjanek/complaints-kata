package pl.tjanek.complaints.adapters.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tjanek.complaints.domain.ComplaintsRepository;

@Configuration(proxyBeanMethods = false)
class DbConfig {

    @Bean
    ComplaintsRepository complaintsRepository(ComplaintsJpaRepository jpaRepository, DbMapper dbMapper) {
        return new ComplaintsRepositoryJpaDbRepository(
            jpaRepository, dbMapper
        );
    }

}
