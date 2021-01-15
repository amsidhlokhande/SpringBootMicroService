package com.amsidh.mvc.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.server.encryption.CipherResourceJsonEncryptor;
import org.springframework.cloud.config.server.encryption.CipherResourcePropertiesEncryptor;
import org.springframework.cloud.config.server.encryption.CipherResourceYamlEncryptor;
import org.springframework.cloud.config.server.encryption.ResourceEncryptor;
import org.springframework.cloud.config.server.encryption.TextEncryptorLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyResourceEncryptorConfiguration {

    @Autowired
    private TextEncryptorLocator encryptor;

    @Bean
    Map<String, ResourceEncryptor> resourceEncryptors() {
        Map<String, ResourceEncryptor> resourceEncryptorMap = new HashMap<>();
        addSupportedExtensionsToMap(resourceEncryptorMap, new CipherResourceJsonEncryptor(encryptor));
        addSupportedExtensionsToMap(resourceEncryptorMap, new CipherResourcePropertiesEncryptor(encryptor));
        addSupportedExtensionsToMap(resourceEncryptorMap, new CipherResourceYamlEncryptor(encryptor));

        return resourceEncryptorMap;
    }

    private void addSupportedExtensionsToMap(
            Map<String, ResourceEncryptor> resourceEncryptorMap,
            ResourceEncryptor resourceEncryptor) {
        for (String ext : resourceEncryptor.getSupportedExtensions()) {
            resourceEncryptorMap.put(ext, resourceEncryptor);
        }
    }
}
