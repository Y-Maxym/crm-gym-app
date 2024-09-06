package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.parser.implementation.TraineeParser;
import com.crm.gym.app.model.parser.implementation.UserParser;
import com.crm.gym.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        traineeStorage.put(id, trainee);

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

        traineeStorage.put(trainee1.getId(), trainee1);
        traineeStorage.put(trainee2.getId(), trainee2);
        traineeStorage.put(trainee3.getId(), trainee3);

        // when
        List<Trainee> actual = traineeStorage.getAll();

        // then
        assertThat(actual).containsExactlyInAnyOrder(trainee1, trainee2, trainee3);
    }

    @Test
    @DisplayName("Test put trainee functionality")
    public void givenTrainee_whenPut_thenNullIsReturned() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoe();

        // when
        Trainee previous = traineeStorage.put(expected.getId(), expected);
        Trainee actual = traineeStorage.get(expected.getId());

        // then
        assertThat(previous).isNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test put trainee functionality")
    public void givenId_whenRemove_thenTraineeIsRemoved() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoe();
        Long id = expected.getId();

        traineeStorage.put(id, expected);

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

        traineeStorage.put(trainee1.getId(), trainee1);
        traineeStorage.put(trainee2.getId(), trainee2);
        traineeStorage.put(trainee3.getId(), trainee3);

        // when
        traineeStorage.clear();

        List<Trainee> actual = traineeStorage.getAll();

        // then
        assertThat(actual).isEmpty();
    }
}