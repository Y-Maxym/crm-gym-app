package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.parser.implementation.TrainingParser;
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
public class TrainingStorage implements Storage<Long, Training> {

    private final Map<Long, Training> storage = new HashMap<>();
    private final TrainingParser parser;

    @Value("${storage.file-path.training}")
    private Resource fileResource;

    @Override
    public Training get(Long key) {
        return storage.get(key);
    }

    @Override
    public Training put(Long key, Training value) {
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

            Training training = parser.parse(properties.getProperty(key));

            storage.put(Long.parseLong(key), training);
        }
    }
}
