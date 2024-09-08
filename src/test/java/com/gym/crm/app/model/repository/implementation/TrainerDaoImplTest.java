package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.storage.implementation.TrainerStorage;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainerDaoImplTest {

    @Mock
    private TrainerStorage storage;

    @InjectMocks
    private TrainerDaoImpl repository;

    @Test
    @DisplayName("Test find trainer by id functionality")
    public void givenId_whenFindById_thenTrainerIsReturned() {
        // given
        Trainer expected = DataUtils.getTrainerEmilyDavis();
        Long id = expected.getId();

        given(storage.get(id))
                .willReturn(expected);

        // when
        Optional<Trainer> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainer by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        Long id = 1L;

        given(storage.get(id))
                .willReturn(null);

        // when
        Optional<Trainer> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all trainer functionality")
    public void givenTrainers_whenFindAll_thenTrainersIsReturned() {
        // given
        Trainer trainer1 = DataUtils.getTrainerDavidBrown();
        Trainer trainer2 = DataUtils.getTrainerEmilyDavis();

        List<Trainer> expected = List.of(trainer1, trainer2);

        given(storage.getAll())
                .willReturn(expected);

        // when
        List<Trainer> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save trainer functionality")
    public void givenTrainerToSave_whenSaveTrainer_thenStorageIsCalled() {
        // given
        Trainer trainerToSave = DataUtils.getTrainerEmilyDavis();

        given(storage.put(trainerToSave))
                .willReturn(trainerToSave);

        // when
        Trainer actual = repository.save(trainerToSave);

        // then
        assertThat(actual).isEqualTo(trainerToSave);

        verify(storage, only()).put(trainerToSave);
    }

    @Test
    @DisplayName("Test update trainer functionality")
    public void givenTrainerToUpdate_whenSaveTrainer_thenStorageIsCalled() {
        // given
        Trainer trainerToUpdate = DataUtils.getTrainerEmilyDavis();

        given(storage.put(trainerToUpdate))
                .willReturn(trainerToUpdate);

        // when
        Trainer actual = repository.update(trainerToUpdate);

        // then
        assertThat(actual).isEqualTo(trainerToUpdate);

        verify(storage, only()).put(trainerToUpdate);
    }

    @Test
    @DisplayName("Test delete trainer by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        Long id = 1L;

        doNothing().when(storage).remove(id);

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id);
    }
}