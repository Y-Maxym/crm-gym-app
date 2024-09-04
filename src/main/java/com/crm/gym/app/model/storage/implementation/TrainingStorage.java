package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.exception.ReadCSVFileException;
import com.crm.gym.app.model.parser.implementation.TrainingParser;
import com.crm.gym.app.model.storage.Storage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
    public List<Training> getAll() {
        return new ArrayList<>(storage.values());
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
    private void init() {
        try (Stream<String> lines = Files.lines(fileResource.getFile().toPath())) {

            lines.skip(1).toList().forEach(line -> {

                Training training = parser.parse(line);
                storage.put(training.getId(), training);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read training file", e);
        }
    }
}
