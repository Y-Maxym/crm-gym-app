package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.storage.implementation.TrainerStorage;
import com.crm.gym.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        Long id = 1L;
        Trainer expected = DataUtils.getTrainerEmilyDavis();

        BDDMockito.given(storage.get(id))
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

        BDDMockito.given(storage.get(id))
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

        BDDMockito.given(storage.getAll())
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

        BDDMockito.given(storage.put(anyLong(), any(Trainer.class)))
                .willReturn(null);

        // when
        repository.save(trainerToSave);

        // then
        verify(storage, only()).put(trainerToSave.getId(), trainerToSave);
    }

    @Test
    @DisplayName("Test update trainer functionality")
    public void givenTrainerToUpdate_whenSaveTrainer_thenStorageIsCalled() {
        // given
        Trainer trainerToUpdate = DataUtils.getTrainerEmilyDavis();
        Trainer previous = DataUtils.getTrainerDavidBrown();

        BDDMockito.given(storage.put(anyLong(), any(Trainer.class)))
                .willReturn(previous);

        // when
        repository.save(trainerToUpdate);

        // then
        verify(storage, only()).put(trainerToUpdate.getId(), trainerToUpdate);
    }

    @Test
    @DisplayName("Test delete trainer by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        Long id = 1L;

        BDDMockito.doNothing().when(storage).remove(anyLong());

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id);
    }
}