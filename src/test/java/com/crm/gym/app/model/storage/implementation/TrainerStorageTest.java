package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.parser.implementation.TrainerParser;
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

        trainerStorage.put(id, trainer);

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

        trainerStorage.put(trainer1.getId(), trainer1);
        trainerStorage.put(trainer2.getId(), trainer2);

        // when
        List<Trainer> actual = trainerStorage.getAll();

        // then
        assertThat(actual).containsExactlyInAnyOrder(trainer1, trainer2);
    }

    @Test
    @DisplayName("Test put trainer functionality")
    public void givenTrainer_whenPut_thenNullIsReturned() {
        // given
        Trainer expected = DataUtils.getTrainerEmilyDavis();

        // when
        Trainer previous = trainerStorage.put(expected.getId(), expected);
        Trainer actual = trainerStorage.get(expected.getId());

        // then
        assertThat(previous).isNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test put trainer functionality")
    public void givenId_whenRemove_thenTrainerIsRemoved() {
        // given
        Trainer expected = DataUtils.getTrainerEmilyDavis();
        Long id = expected.getId();

        trainerStorage.put(id, expected);

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

        trainerStorage.put(trainer1.getId(), trainer1);
        trainerStorage.put(trainer2.getId(), trainer2);

        // when
        trainerStorage.clear();

        List<Trainer> actual = trainerStorage.getAll();

        // then
        assertThat(actual).isEmpty();
    }
}