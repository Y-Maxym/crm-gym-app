package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.parser.implementation.TrainerParser;
import com.crm.gym.app.model.storage.Storage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class TrainerStorage implements Storage<Long, Trainer> {

    private final Map<Long, Trainer> storage = new HashMap<>();
    private final TrainerParser parser;

    @Value("${storage.file-path.trainee}")
    private Resource fileResource;

    @Override
    public Trainer get(Long key) {
        return storage.get(key);
    }

    @Override
    public Trainer put(Long key, Trainer value) {
        return storage.put(key, value);
    }

    @Override
    public void remove(Long key) {
        storage.remove(key);
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        Properties properties = new Properties();
        properties.load(fileResource.getInputStream());

        for (String key : properties.stringPropertyNames()) {

            Trainer trainer = parser.parse(properties.getProperty(key));

            storage.put(Long.parseLong(key), trainer);
        }
    }
}
