package com.gym.crm.app.model.storage.implementation;

import com.gym.crm.app.exception.ReadCSVFileException;
import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.parser.implementation.TrainerParser;
import com.gym.crm.app.model.parser.implementation.UserParser;
import com.gym.crm.app.model.storage.Storage;
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
public class TrainerStorage implements Storage<Long, Trainer> {

    private final Map<Long, Trainer> storage = new HashMap<>();

    private static long currentId = 1;

    private TrainerParser trainerParser;
    private UserParser userParser;
    private UserStorage userStorage;

    @Value("${storage.file-path.trainer}")
    @Setter(AccessLevel.NONE)
    private Resource fileResource;

    @Override
    public Trainer get(Long id) {
        return storage.get(id);
    }

    @Override
    public List<Trainer> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Trainer put(Trainer entity) {
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

                User user = userParser.parse(line);
                user = userStorage.put(user);

                Trainer trainer = trainerParser.parse(line);
                trainer.setUserId(user.getId());
                put(trainer);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read trainer file", e);
        }
    }
}
