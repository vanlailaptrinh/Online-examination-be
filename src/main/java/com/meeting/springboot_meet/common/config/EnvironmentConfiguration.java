package com.meeting.springboot_meet.common.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class that loads environment variables from .env file
 * and registers them as a property source for Spring's placeholder resolution.
 */
@Configuration
public class EnvironmentConfiguration {

    private final Environment environment;

    public EnvironmentConfiguration(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadDotenvVariables() {
        if (environment instanceof ConfigurableEnvironment) {
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
            
            // Load .env file
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            // Convert to a property source
            Map<String, Object> dotenvProperties = new HashMap<>();
            dotenv.entries().forEach(entry -> {
                dotenvProperties.put(entry.getKey(), entry.getValue());
                // Also add lowercase-hyphenated versions for Spring Boot convention
                String springKey = entry.getKey().toLowerCase().replace('_', '-');
                if (!springKey.equals(entry.getKey().toLowerCase())) {
                    dotenvProperties.put(springKey, entry.getValue());
                }
            });

            // Register as the highest priority property source
            PropertySource<?> propertySource = new MapPropertySource("dotenv", dotenvProperties);
            configurableEnvironment.getPropertySources().addFirst(propertySource);
        }
    }
}
