package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.exception.ReadCSVFileException;
import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.parser.implementation.TrainingParser;
import com.crm.gym.app.model.storage.Storage;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
@Setter(onMethod_ = @Autowired)
public class TrainingStorage implements Storage<Long, Training> {

    private final Map<Long, Training> storage = new HashMap<>();

    private static long currentId = 1;

    private TrainingParser parser;

    @Value("${storage.file-path.training}")
    @Setter(AccessLevel.NONE)
    private Resource fileResource;

    @Override
    public Training get(Long id) {
        return storage.get(id);
    }

    @Override
    public List<Training> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Training put(Training entity) {
        if (isNull(entity.getId())) {
            entity.setId(currentId++);
        }

        storage.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public void remove(Long id) {
        storage.remove(id);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @PostConstruct
    private void init() {
        try (Stream<String> lines = Files.lines(fileResource.getFile().toPath())) {

            lines.skip(1).toList().forEach(line -> {

                Training training = parser.parse(line);
                put(training);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read training file", e);
        }
    }
}
