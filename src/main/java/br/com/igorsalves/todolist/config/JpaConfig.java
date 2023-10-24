package br.com.igorsalves.todolist.config;

import java.sql.Types;

import org.hibernate.MappingException;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {
    @Bean
    public H2Dialect h2Dialect() {
        return new H2Dialect() {
            @Override
            public String getHibernateTypeName(int code, int length, int precision, int scale) throws MappingException {
                if (code == Types.OTHER) {
                    return "uuid";
                }
                return super.getHibernateTypeName(code, length, precision, scale);
            }
        };
    }
}

