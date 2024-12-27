package com.example.lab_java_web.featuretoggle.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.feature")
public class FeatureToggleProperties {
    private Map<String, Boolean> toggles;

    public boolean check(String featureToggle) {
        return toggles.getOrDefault(featureToggle, false);
    }
}
