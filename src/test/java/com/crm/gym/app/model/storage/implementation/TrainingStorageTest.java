package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.exception.ParseException;
import com.crm.gym.app.exception.ReadCSVFileException;
import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.parser.implementation.TrainingParser;
import com.crm.gym.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainingStorageTest {

    @Mock
    private TrainingParser trainingParser;

    @InjectMocks
    private TrainingStorage trainingStorage;

    @Test
    @DisplayName("Test get training by key functionality")
    public void givenId_whenGet_thenTrainingIsReturned() {
        // given
        Training training = DataUtils.getTrainingDavidBrown();
        Long id = training.getId();

        trainingStorage.put(id, training);

        // when
        Training actual = trainingStorage.get(id);

        // then
        assertThat(actual).isEqualTo(training);
    }

    @Test
    @DisplayName("Test get all trainings functionality")
    public void givenTrainings_whenGetAll_thenTrainingsIsReturned() {
        // given
        Training training1 = DataUtils.getTrainingDavidBrown();
        Training training2 = DataUtils.getTrainingEmilyDavis();

        trainingStorage.put(training1.getId(), training1);
        trainingStorage.put(training2.getId(), training2);

        // when
        List<Training> actual = trainingStorage.getAll();

        // then
        assertThat(actual).containsExactlyInAnyOrder(training1, training2);
    }

    @Test
    @DisplayName("Test put training functionality")
    public void givenTraining_whenPut_thenNullIsReturned() {
        // given
        Training expected = DataUtils.getTrainingDavidBrown();

        // when
        Training previous = trainingStorage.put(expected.getId(), expected);
        Training actual = trainingStorage.get(expected.getId());

        // then
        assertThat(previous).isNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test put training functionality")
    public void givenId_whenRemove_thenTrainingIsRemoved() {
        // given
        Training expected = DataUtils.getTrainingDavidBrown();
        Long id = expected.getId();

        trainingStorage.put(id, expected);

        // when
        trainingStorage.remove(id);

        Training actual = trainingStorage.get(id);

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test clear training storage functionality")
    public void givenTrainings_whenClear_thenTrainingsIsRemoved() {
        // given
        Training training1 = DataUtils.getTrainingDavidBrown();
        Training training2 = DataUtils.getTrainingEmilyDavis();

        trainingStorage.put(training1.getId(), training1);
        trainingStorage.put(training2.getId(), training2);

        // when
        trainingStorage.clear();

        List<Training> actual = trainingStorage.getAll();

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test init storage via correct file path functionality")
    public void givenCorrectPath_whenInitStorage_thenStorageIsInitialized() {
        // given
        Training training = DataUtils.getTrainingEmilyDavis();

        given(trainingParser.parse(anyString()))
                .willReturn(training);

        Resource resource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(trainingStorage, "fileResource", resource);

        // when
        ReflectionTestUtils.invokeMethod(trainingStorage, "init");

        List<Training> trainings = trainingStorage.getAll();

        // then
        assertThat(trainings).isNotNull();
        assertThat(trainings).hasSize(1);
        assertThat(trainings).extracting("id").contains(training.getId());
    }

    @Test
    @DisplayName("Test init storage via incorrect file path functionality")
    public void givenIncorrectFilePath_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/incorrect-training-test.csv");
        ReflectionTestUtils.setField(trainingStorage, "fileResource", resource);

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(trainingStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read training file");

        verify(trainingParser, never()).parse(anyString());
    }

    @Test
    @DisplayName("Test init storage via incorrect training data functionality")
    public void givenIncorrectTrainingData_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/training-test.csv");
        ReflectionTestUtils.setField(trainingStorage, "fileResource", resource);

        given(trainingParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(trainingStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read training file");
        assertThat(ex.getCause()).isInstanceOf(ParseException.class);
        assertThat(ex.getCause().getMessage()).isEqualTo("Value cannot be null");
    }
}