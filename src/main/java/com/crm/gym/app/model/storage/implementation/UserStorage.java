package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.exception.ReadCSVFileException;
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
public class UserStorage implements Storage<Long, User> {

    private final Map<Long, User> storage = new HashMap<>();
    private final UserParser parser;

    @Value("${storage.file-path.user}")
    private Resource fileResource;

    @Override
    public User get(Long key) {
        return storage.get(key);
    }

    @Override
    public User put(Long key, User value) {
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

                User user = parser.parse(line);

                storage.put(user.getId(), user);
            });

        } catch (IOException e) {
            throw new ReadCSVFileException("Failed to read user file", e);
        }
    }
}
