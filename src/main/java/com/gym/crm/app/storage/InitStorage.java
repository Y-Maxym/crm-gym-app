package com.gym.crm.app.storage;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.CSVFileReadException;
import com.gym.crm.app.parser.impl.TraineeParser;
import com.gym.crm.app.parser.impl.TrainerParser;
import com.gym.crm.app.parser.impl.TrainingParser;
import com.gym.crm.app.parser.impl.UserParser;
import com.gym.crm.app.repository.EntityDao;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

@Component
@Setter(onMethod_ = @Autowired)
public class InitStorage {

    private static final String INITIALIZE_DATA_ERROR = "Failed to initialize data from CSV files";

    private TraineeParser traineeParser;
    private TrainerParser trainerParser;
    private UserParser userParser;
    private TrainingParser trainingParser;

    private EntityDao<Long, Trainee> traineeRepository;
    private EntityDao<Long, Trainer> trainerRepository;
    private EntityDao<Long, Training> trainingRepository;
    private EntityDao<Long, User> userRepository;

    @Value("${storage.file-path.trainee}")
    @Setter(AccessLevel.NONE)
    private Resource traineeSource;

    @Value("${storage.file-path.trainer}")
    @Setter(AccessLevel.NONE)
    private Resource trainerSource;

    @Value("${storage.file-path.training}")
    @Setter(AccessLevel.NONE)
    private Resource trainingSource;

    @PostConstruct
    private void init() {
        try {
            initTraineeStorage();
            initTrainerStorage();
            initTrainingStorage();
        } catch (Exception e) {
            throw new CSVFileReadException(INITIALIZE_DATA_ERROR, e);
        }
    }

    private void initTraineeStorage() throws IOException {
        try (Stream<String> lines = Files.lines(traineeSource.getFile().toPath())) {

            List<String> dataLines = lines.skip(1).toList();
            dataLines.forEach(this::processTraineeLine);
        }
    }

    private void initTrainerStorage() throws IOException {
        try (Stream<String> lines = Files.lines(trainerSource.getFile().toPath())) {

            List<String> dataLines = lines.skip(1).toList();
            dataLines.forEach(this::processTrainerLine);
        }
    }

    private void initTrainingStorage() throws IOException {
        try (Stream<String> lines = Files.lines(trainingSource.getFile().toPath())) {

            List<String> dataLines = lines.skip(1).toList();
            dataLines.forEach(this::processTrainingLine);
        }
    }

    private void processTraineeLine(String line) {
        User user = userParser.parse(line);
        User savedUser = userRepository.save(user);

        Trainee trainee = traineeParser.parse(line);
        Trainee updatedTrainee = trainee.toBuilder().userId(savedUser.getId()).build();

        traineeRepository.save(updatedTrainee);
    }

    private void processTrainerLine(String line) {
        User user = userParser.parse(line);
        User savedUser = userRepository.save(user);

        Trainer trainer = trainerParser.parse(line);
        Trainer updatedTrainer = trainer.toBuilder().userId(savedUser.getId()).build();

        trainerRepository.save(updatedTrainer);
    }

    private void processTrainingLine(String line) {
        Training training = trainingParser.parse(line);
        trainingRepository.save(training);
    }
}
