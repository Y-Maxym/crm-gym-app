package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.exception.ParseException;
import com.crm.gym.app.exception.ReadCSVFileException;
import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.implementation.TrainerParser;
import com.crm.gym.app.model.parser.implementation.UserParser;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainerStorageTest {

    @Mock
    private TrainerParser trainerParser;

    @Mock
    private UserStorage userStorage;

    @Mock
    private UserParser userParser;

    @InjectMocks
    private TrainerStorage trainerStorage;

    @Test
    @DisplayName("Test get trainer by key functionality")
    public void givenId_whenGet_thenTrainerIsReturned() {
        // given
        Trainer trainer = DataUtils.getTrainerDavidBrown();
        Long id = trainer.getId();

        trainerStorage.put(trainer);

        // when
        Trainer actual = trainerStorage.get(id);

        // then
        assertThat(actual).isEqualTo(trainer);
    }

    @Test
    @DisplayName("Test get all trainers functionality")
    public void givenTrainers_whenGetAll_thenTrainersIsReturned() {
        // given
        Trainer trainer1 = DataUtils.getTrainerEmilyDavis();
        Trainer trainer2 = DataUtils.getTrainerDavidBrown();

        trainerStorage.put(trainer1);
        trainerStorage.put(trainer2);

        // when
        List<Trainer> actual = trainerStorage.getAll();

        // then
        assertThat(actual).containsExactlyInAnyOrder(trainer1, trainer2);
    }

    @Test
    @DisplayName("Test put trainer functionality")
    public void givenTrainer_whenPut_thenTrainerIsReturned() {
        // given
        Trainer expected = DataUtils.getTrainerEmilyDavis();

        // when
        Trainer actual = trainerStorage.put(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test remove trainer functionality")
    public void givenId_whenRemove_thenTrainerIsRemoved() {
        // given
        Trainer expected = DataUtils.getTrainerEmilyDavis();
        Long id = expected.getId();

        trainerStorage.put(expected);

        // when
        trainerStorage.remove(id);

        Trainer actual = trainerStorage.get(id);

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test clear trainer storage functionality")
    public void givenTrainers_whenClear_thenTrainersIsRemoved() {
        // given
        Trainer trainer1 = DataUtils.getTrainerEmilyDavis();
        Trainer trainer2 = DataUtils.getTrainerDavidBrown();

        trainerStorage.put(trainer1);
        trainerStorage.put(trainer2);

        // when
        trainerStorage.clear();

        List<Trainer> actual = trainerStorage.getAll();

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test init storage via correct file path functionality")
    public void givenCorrectPath_whenInitStorage_thenStorageIsInitialized() {
        // given
        Trainer trainer = DataUtils.getTrainerEmilyDavis();
        User user = DataUtils.getUserJohnDoe();

        given(userParser.parse(anyString()))
                .willReturn(user);

        given(userStorage.put(user))
                .willReturn(user);

        given(trainerParser.parse(anyString()))
                .willReturn(trainer);

        Resource resource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(trainerStorage, "fileResource", resource);

        // when
        ReflectionTestUtils.invokeMethod(trainerStorage, "init");

        List<Trainer> trainers = trainerStorage.getAll();

        // then
        assertThat(trainers).isNotNull();
        assertThat(trainers).hasSize(1);
        assertThat(trainers).extracting("userId").contains(trainer.getUserId());

        verify(userStorage, only()).put(user);
    }

    @Test
    @DisplayName("Test init storage via incorrect file path functionality")
    public void givenIncorrectFilePath_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/incorrect-trainer-test.csv");
        ReflectionTestUtils.setField(trainerStorage, "fileResource", resource);

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(trainerStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read trainer file");

        verify(userParser, never()).parse(anyString());
        verify(userStorage, never()).put(any(User.class));
        verify(trainerParser, never()).parse(anyString());
    }

    @Test
    @DisplayName("Test init storage via incorrect user data functionality")
    public void givenIncorrectUserData_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(trainerStorage, "fileResource", resource);

        given(userParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(trainerStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read trainer file");
        assertThat(ex.getCause()).isInstanceOf(ParseException.class);
        assertThat(ex.getCause().getMessage()).isEqualTo("Value cannot be null");

        verify(userStorage, never()).put(any(User.class));
        verify(trainerParser, never()).parse(anyString());
    }

    @Test
    @DisplayName("Test init storage via incorrect trainer data functionality")
    public void givenIncorrectTrainerData_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/trainer-test.csv");
        ReflectionTestUtils.setField(trainerStorage, "fileResource", resource);

        User user = DataUtils.getUserJohnDoe();

        given(userParser.parse(anyString()))
                .willReturn(user);

        given(trainerParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(trainerStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read trainer file");
        assertThat(ex.getCause()).isInstanceOf(ParseException.class);
        assertThat(ex.getCause().getMessage()).isEqualTo("Value cannot be null");
    }
}