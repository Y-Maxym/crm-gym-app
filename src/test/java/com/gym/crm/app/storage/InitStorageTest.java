package com.gym.crm.app.storage;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.CSVFileReadException;
import com.gym.crm.app.exception.ParseException;
import com.gym.crm.app.parser.impl.TraineeParser;
import com.gym.crm.app.parser.impl.TrainerParser;
import com.gym.crm.app.parser.impl.TrainingParser;
import com.gym.crm.app.parser.impl.UserParser;
import com.gym.crm.app.repository.impl.TraineeDaoImpl;
import com.gym.crm.app.repository.impl.TrainerDaoImpl;
import com.gym.crm.app.repository.impl.TrainingDaoImpl;
import com.gym.crm.app.repository.impl.UserDaoImpl;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InitStorageTest {

    @InjectMocks
    private InitStorage initStorage;

    @Mock
    private TraineeParser traineeParser;

    @Mock
    private TrainerParser trainerParser;

    @Mock
    private UserParser userParser;

    @Mock
    private TrainingParser trainingParser;

    @Mock
    private TraineeDaoImpl traineeRepository;

    @Mock
    private TrainerDaoImpl trainerRepository;

    @Mock
    private TrainingDaoImpl trainingRepository;

    @Mock
    private UserDaoImpl userRepository;

    @Test
    @DisplayName("Test init trainee storage via correct file path functionality")
    public void givenCorrectPath_whenInitTraineeStorage_thenStorageIsInitialized() {
        // given
        Resource resource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", resource);

        Trainee traineeTransient = DataUtils.getTraineeJohnDoeTransient();
        User userTransient = DataUtils.getUserJohnDoeTransient();

        User userPersisted = DataUtils.getUserJohnDoePersisted();

        given(userParser.parse(anyString())).willReturn(userTransient);
        given(userRepository.save(userTransient))
                .willReturn(userPersisted);

        given(traineeParser.parse(anyString())).willReturn(traineeTransient);

        // when
        ReflectionTestUtils.invokeMethod(initStorage, "initTraineeStorage");

        // then
        verify(traineeRepository).save(any(Trainee.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Test init trainer storage via correct file path functionality")
    public void givenCorrectPath_whenInitTrainerStorage_thenStorageIsInitialized() {
        // given
        Resource resource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", resource);

        Trainer trainerTransient = DataUtils.getTrainerEmilyDavisTransient();
        User userTransient = DataUtils.getUserEmilyDavisTransient();

        User userPersisted = DataUtils.getUserEmilyDavisPersisted();

        given(userParser.parse(anyString())).willReturn(userTransient);
        given(userRepository.save(userTransient))
                .willReturn(userPersisted);

        given(trainerParser.parse(anyString())).willReturn(trainerTransient);

        // when
        ReflectionTestUtils.invokeMethod(initStorage, "initTrainerStorage");

        // then
        verify(trainerRepository).save(any(Trainer.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Test init training storage via correct file path functionality")
    public void givenCorrectPath_whenInitTrainingStorage_thenStorageIsInitialized() {
        // given
        Resource resource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", resource);

        Training trainingTransient = DataUtils.getTrainingEmilyDavisTransient();

        given(trainingParser.parse(anyString())).willReturn(trainingTransient);

        // when
        ReflectionTestUtils.invokeMethod(initStorage, "initTrainingStorage");

        // then
        verify(trainingRepository).save(any(Training.class));
    }

    @Test
    @DisplayName("Test init trainee storage via incorrect file path functionality")
    public void givenIncorrectFilePath_whenInitTraineeStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/incorrect-trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        // when
        CSVFileReadException ex = assertThrows(CSVFileReadException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "init");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to initialize data from CSV files");

        verify(userParser, never()).parse(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(traineeParser, never()).parse(anyString());
        verify(traineeRepository, never()).save(any(Trainee.class));
    }

    @Test
    @DisplayName("Test init trainer storage via incorrect file path functionality")
    public void givenIncorrectFilePath_whenInitTrainerStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/incorrect-trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        // when
        CSVFileReadException ex = assertThrows(CSVFileReadException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "init");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to initialize data from CSV files");

        verify(trainerParser, never()).parse(anyString());
        verify(traineeRepository, never()).save(any(Trainee.class));
    }

    @Test
    @DisplayName("Test init training storage via incorrect file path functionality")
    public void givenIncorrectFilePath_whenInitTrainingStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/incorrect-training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        // when
        CSVFileReadException ex = assertThrows(CSVFileReadException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "init");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to initialize data from CSV files");

        verify(trainingRepository, never()).save(any(Training.class));
        verify(trainingParser, never()).parse(anyString());
    }

    @Test
    @DisplayName("Test init trainee storage via incorrect user data functionality")
    public void givenIncorrectUserData_whenInitTraineeStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        given(userParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "initTraineeStorage");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Value cannot be null");

        verify(userRepository, never()).save(any(User.class));
        verify(traineeParser, never()).parse(anyString());
        verify(traineeRepository, never()).save(any(Trainee.class));
    }

    @Test
    @DisplayName("Test init trainee storage via incorrect trainee data functionality")
    public void givenIncorrectTraineeData_whenInitTraineeStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        User userTransient = DataUtils.getUserJohnDoeTransient();
        User userPersisted = DataUtils.getUserJohnDoePersisted();

        given(userParser.parse(anyString()))
                .willReturn(userTransient);

        given(userRepository.save(any(User.class)))
                .willReturn(userPersisted);

        given(traineeParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "initTraineeStorage");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Value cannot be null");

        verify(userParser, times(1)).parse(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(traineeParser, times(1)).parse(anyString());
        verify(traineeRepository, never()).save(any(Trainee.class));
    }

    @Test
    @DisplayName("Test init trainer storage via incorrect user data functionality")
    public void givenIncorrectUserData_whenInitTrainerStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        given(userParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "initTrainerStorage");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Value cannot be null");

        verify(trainerParser, never()).parse(anyString());
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    @Test
    @DisplayName("Test init trainer storage via incorrect trainer data functionality")
    public void givenIncorrectTrainerData_whenInitTrainerStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        User userTransient = DataUtils.getUserJohnDoeTransient();
        User userPersisted = DataUtils.getUserJohnDoePersisted();

        given(userParser.parse(anyString()))
                .willReturn(userTransient);

        given(userRepository.save(any(User.class)))
                .willReturn(userPersisted);

        given(trainerParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "initTrainerStorage");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Value cannot be null");

        verify(trainerParser, times(1)).parse(anyString());
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    @Test
    @DisplayName("Test init training storage via incorrect training data functionality")
    public void givenIncorrectTrainingData_whenInitTrainingStorage_thenExceptionIsThrown() {
        // given
        Resource traineeResource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(initStorage, "traineeSource", traineeResource);

        Resource trainerResource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainerSource", trainerResource);

        Resource trainingResource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(initStorage, "trainingSource", trainingResource);

        given(trainingParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> {
            ReflectionTestUtils.invokeMethod(initStorage, "initTrainingStorage");
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Value cannot be null");

        verify(trainingParser, times(1)).parse(anyString());
        verify(trainingRepository, never()).save(any(Training.class));
    }
}
