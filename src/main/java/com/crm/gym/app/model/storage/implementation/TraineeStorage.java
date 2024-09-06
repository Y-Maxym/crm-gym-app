package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.exception.ReadCSVFileException;
import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.implementation.TraineeParser;
import com.crm.gym.app.model.parser.implementation.UserParser;
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
public class TraineeStorage implements Storage<Long, Trainee> {

    private final Map<Long, Trainee> storage = new HashMap<>();
    private final TraineeParser traineeParser;

    private final UserStorage userStorage;
    private final UserParser userParser;

    @Value("${storage.file-path.trainee}")
    private Resource fileResource;

    @Override
    public Trainee get(Long key) {
        return storage.get(key);
    }

    @Override
    public List<Trainee> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Trainee put(Long key, Trainee value) {
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

                Trainee trainee = traineeParser.parse(line);
                storage.put(trainee.getId(), trainee);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read trainee file", e);
        }
    }
}
