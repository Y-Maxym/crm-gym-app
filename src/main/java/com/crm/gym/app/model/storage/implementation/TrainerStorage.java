package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.exception.ReadCSVFileException;
import com.crm.gym.app.model.parser.implementation.TrainerParser;
import com.crm.gym.app.model.storage.Storage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TrainerStorage implements Storage<Long, Trainer> {

    private final Map<Long, Trainer> storage = new HashMap<>();
    private final TrainerParser parser;

    @Value("${storage.file-path.trainer}")
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
    private void init() {
        try (Stream<String> lines = Files.lines(fileResource.getFile().toPath())) {

            lines.skip(1).toList().forEach(line -> {

                Trainer trainer = parser.parse(line);

                storage.put(trainer.getId(), trainer);
            });

        } catch (IOException e) {
            throw new ReadCSVFileException("Failed to read trainer file", e);
        }
    }
}
