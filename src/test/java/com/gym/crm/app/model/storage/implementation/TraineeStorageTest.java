package com.gym.crm.app.model.storage.implementation;

import com.gym.crm.app.exception.ParseException;
import com.gym.crm.app.exception.ReadCSVFileException;
import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.parser.implementation.TraineeParser;
import com.gym.crm.app.model.parser.implementation.UserParser;
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
class TraineeStorageTest {

    @Mock
    private TraineeParser traineeParser;

    @Mock
    private UserStorage userStorage;

    @Mock
    private UserParser userParser;

    @InjectMocks
    private TraineeStorage traineeStorage;

    @Test
    @DisplayName("Test get trainee by key functionality")
    public void givenId_whenGet_thenTraineeIsReturned() {
        // given
        Trainee trainee = DataUtils.getTraineeJohnDoe();
        Long id = trainee.getId();

        traineeStorage.put(trainee);

        // when
        Trainee actual = traineeStorage.get(id);

        // then
        assertThat(actual).isEqualTo(trainee);
    }

    @Test
    @DisplayName("Test get all trainees functionality")
    public void givenTrainees_whenGetAll_thenTraineesIsReturned() {
        // given
        Trainee trainee1 = DataUtils.getTraineeJohnDoe();
        Trainee trainee2 = DataUtils.getTraineeJaneSmith();
        Trainee trainee3 = DataUtils.getTraineeMichaelJohnson();

        traineeStorage.put(trainee1);
        traineeStorage.put(trainee2);
        traineeStorage.put(trainee3);

        // when
        List<Trainee> actual = traineeStorage.getAll();

        // then
        assertThat(actual).containsExactlyInAnyOrder(trainee1, trainee2, trainee3);
    }

    @Test
    @DisplayName("Test put trainee functionality")
    public void givenTrainee_whenPut_thenTraineeIsReturned() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoe();

        // when
        Trainee actual = traineeStorage.put(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test remove trainee functionality")
    public void givenId_whenRemove_thenTraineeIsRemoved() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoe();
        Long id = expected.getId();

        traineeStorage.put(expected);

        // when
        traineeStorage.remove(id);

        Trainee actual = traineeStorage.get(id);

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test clear trainee storage functionality")
    public void givenTrainees_whenClear_thenTraineesIsRemoved() {
        // given
        Trainee trainee1 = DataUtils.getTraineeJohnDoe();
        Trainee trainee2 = DataUtils.getTraineeJaneSmith();
        Trainee trainee3 = DataUtils.getTraineeMichaelJohnson();

        traineeStorage.put(trainee1);
        traineeStorage.put(trainee2);
        traineeStorage.put(trainee3);

        // when
        traineeStorage.clear();

        List<Trainee> actual = traineeStorage.getAll();

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test init storage via correct file path functionality")
    public void givenCorrectPath_whenInitStorage_thenStorageIsInitialized() {
        // given
        Trainee trainee = DataUtils.getTraineeJohnDoe();
        User user = DataUtils.getUserJohnDoe();

        given(userParser.parse(anyString()))
                .willReturn(user);

        given(userStorage.put(user))
                .willReturn(user);

        given(traineeParser.parse(anyString()))
                .willReturn(trainee);


        Resource resource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(traineeStorage, "fileResource", resource);

        // when
        ReflectionTestUtils.invokeMethod(traineeStorage, "init");

        List<Trainee> trainees = traineeStorage.getAll();

        // then
        assertThat(trainees).isNotNull();
        assertThat(trainees).hasSize(1);
        assertThat(trainees).extracting("address").contains(trainee.getAddress());

        verify(userStorage, only()).put(user);
    }

    @Test
    @DisplayName("Test init storage via incorrect file path functionality")
    public void givenIncorrectFilePath_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/incorrect-trainee-test.csv");
        ReflectionTestUtils.setField(traineeStorage, "fileResource", resource);

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(traineeStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read trainee file");

        verify(userParser, never()).parse(anyString());
        verify(userStorage, never()).put(any(User.class));
        verify(traineeParser, never()).parse(anyString());
    }

    @Test
    @DisplayName("Test init storage via incorrect user data functionality")
    public void givenIncorrectUserData_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(traineeStorage, "fileResource", resource);

        given(userParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(traineeStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read trainee file");
        assertThat(ex.getCause()).isInstanceOf(ParseException.class);
        assertThat(ex.getCause().getMessage()).isEqualTo("Value cannot be null");

        verify(userStorage, never()).put(any(User.class));
        verify(traineeParser, never()).parse(anyString());
    }

    @Test
    @DisplayName("Test init storage via incorrect trainee data functionality")
    public void givenIncorrectTraineeData_whenInitStorage_thenExceptionIsThrown() {
        // given
        Resource resource = new ClassPathResource("init/trainee-test.csv");
        ReflectionTestUtils.setField(traineeStorage, "fileResource", resource);

        User user = DataUtils.getUserJohnDoe();

        given(userParser.parse(anyString()))
                .willReturn(user);

        given(traineeParser.parse(anyString()))
                .willThrow(new ParseException("Value cannot be null"));

        // when
        ReadCSVFileException ex = assertThrows(ReadCSVFileException.class, () -> ReflectionTestUtils.invokeMethod(traineeStorage, "init"));

        // then
        assertThat(ex.getMessage()).isEqualTo("Failed to read trainee file");
        assertThat(ex.getCause()).isInstanceOf(ParseException.class);
        assertThat(ex.getCause().getMessage()).isEqualTo("Value cannot be null");
    }

}