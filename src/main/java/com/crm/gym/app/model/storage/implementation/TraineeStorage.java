package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.exception.ReadCSVFileException;
import com.crm.gym.app.model.parser.implementation.TraineeParser;
import com.crm.gym.app.model.parser.implementation.UserParser;
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
public class TraineeStorage implements Storage<Long, Trainee> {

    private final Map<Long, Trainee> traineeStorage = new HashMap<>();
    private final TraineeParser traineeParser;

    private final UserStorage userStorage;
    private final UserParser userParser;

    @Value("${storage.file-path.trainee}")
    private Resource fileResource;

    @Override
    public Trainee get(Long key) {
        return traineeStorage.get(key);
    }

    @Override
    public Trainee put(Long key, Trainee value) {
        return traineeStorage.put(key, value);
    }

    @Override
    public void remove(Long key) {
        traineeStorage.remove(key);
    }

    @PostConstruct
    private void init() {
        try (Stream<String> lines = Files.lines(fileResource.getFile().toPath())) {

            lines.skip(1).toList().forEach(line -> {

                User user = userParser.parse(line);
                userStorage.put(user.getId(), user);

                Trainee trainee = traineeParser.parse(line);
                traineeStorage.put(trainee.getId(), trainee);
            });

        } catch (IOException e) {
            throw new ReadCSVFileException("Failed to read trainee file", e);
        }
    }
}
