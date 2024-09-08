package com.gym.crm.app.util;

import com.gym.crm.app.exception.ReadCSVFileException;
import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.entity.Training;
import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.parser.implementation.TraineeParser;
import com.gym.crm.app.model.parser.implementation.TrainerParser;
import com.gym.crm.app.model.parser.implementation.TrainingParser;
import com.gym.crm.app.model.parser.implementation.UserParser;
import com.gym.crm.app.model.repository.EntityDao;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.util.stream.Stream;

@Component
@Setter(onMethod_ = @Autowired)
public class InitDataUtils {

    private TraineeParser traineeParser;
    private TrainerParser trainerParser;
    private UserParser userParser;
    private TrainingParser trainingParser;

    private EntityDao<Long, Trainee> traineeRepository;
    private EntityDao<Long, Trainer> trainerRepository;
    private EntityDao<Long, Training> trainingRepository;
    private EntityDao<Long, User> userRepository;

    private void initTraineeStorage() {
        Resource fileResource = new ClassPathResource("init/trainee.csv");

        try (Stream<String> lines = Files.lines(fileResource.getFile().toPath())) {

            lines.skip(1).toList().forEach(line -> {

                User user = userParser.parse(line);
                user = userRepository.saveOrUpdate(user);

                Trainee trainee = traineeParser.parse(line);
                trainee.setUserId(user.getId());
                traineeRepository.saveOrUpdate(trainee);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read trainee file", e);
        }
    }

    private void initTrainerStorage() {
        Resource fileResource = new ClassPathResource("init/trainer.csv");

        try (Stream<String> lines = Files.lines(fileResource.getFile().toPath())) {

            lines.skip(1).toList().forEach(line -> {

                User user = userParser.parse(line);
                user = userRepository.saveOrUpdate(user);

                Trainer trainer = trainerParser.parse(line);
                trainer.setUserId(user.getId());
                trainerRepository.saveOrUpdate(trainer);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read trainer file", e);
        }
    }

    private void initTrainingStorage() {
        Resource fileResource = new ClassPathResource("init/training.csv");

        try (Stream<String> lines = Files.lines(fileResource.getFile().toPath())) {

            lines.skip(1).toList().forEach(line -> {

                Training training = trainingParser.parse(line);
                trainingRepository.saveOrUpdate(training);
            });

        } catch (Exception e) {
            throw new ReadCSVFileException("Failed to read training file", e);
        }
    }

    @PostConstruct
    private void init() {
        initTraineeStorage();
        initTrainerStorage();
        initTrainingStorage();
    }
}
