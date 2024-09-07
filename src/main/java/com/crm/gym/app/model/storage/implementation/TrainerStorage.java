package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.exception.ReadCSVFileException;
import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.implementation.TrainerParser;
import com.crm.gym.app.model.parser.implementation.UserParser;
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

@Component
@Setter(onMethod_ = @Autowired)
public class TrainerStorage implements Storage<Long, Trainer> {

    private final Map<Long, Trainer> storage = new HashMap<>();

    private TrainerParser trainerParser;
    private UserParser userParser;
    private UserStorage userStorage;

    @Value("${storage.file-path.trainer}")
    @Setter(AccessLevel.NONE)
    private Resource fileResource;

    @Override
    public Trainer get(Long key) {
        return storage.get(key);
    }

    @Override
    public List<Trainer> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Trainer put(Long key, Trainer value) {
        return storage.put(key, value);
    }

    @Override
    public void remove(Long key) {
        storage.remove(key);
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
                userStorage.put(user.getId(), user);

                Trainer trainer = trainerParser.parse(line);
                storage.put(trainer.getId(), trainer);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read trainer file", e);
        }
    }
}
