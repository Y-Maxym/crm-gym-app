package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.storage.Storage;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class TrainerDaoImplTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private TrainerDaoImpl repository;

    @Test
    @DisplayName("Test find trainer by id functionality")
    public void givenId_whenFindById_thenTrainerIsReturned() {
        // given
        Trainer expected = DataUtils.getTrainerEmilyDavisPersisted();
        Long id = expected.getId();

        given(storage.get(id, Trainer.class))
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

        given(storage.get(id, Trainer.class))
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
        Trainer trainer1 = DataUtils.getTrainerDavidBrownPersisted();
        Trainer trainer2 = DataUtils.getTrainerEmilyDavisPersisted();

        List<Trainer> expected = List.of(trainer1, trainer2);

        given(storage.getAll(Trainer.class))
                .willReturn(expected);

        // when
        List<Trainer> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save trainer functionality")
    public void givenTrainerToSave_whenSaveOrUpdateTrainer_thenStorageIsCalled() {
        // given
        Trainer trainerToSave = spy(DataUtils.getTrainerEmilyDavisTransient());
        Trainer persisted = DataUtils.getTrainerEmilyDavisPersisted();

        given(storage.put(anyLong(), eq(persisted)))
                .willReturn(persisted);

        // when
        Trainer actual = repository.saveOrUpdate(trainerToSave);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isEqualTo(persisted);

        verify(trainerToSave, times(1)).toBuilder();
        verify(storage, only()).put(anyLong(), eq(persisted));
    }

    @Test
    @DisplayName("Test update trainer functionality")
    public void givenTrainerToUpdate_whenSaveOrUpdateTrainer_thenStorageIsCalled() {
        // given
        Trainer trainerToUpdate = spy(DataUtils.getTrainerEmilyDavisPersisted());
        Long id = trainerToUpdate.getId();

        given(storage.put(id, trainerToUpdate))
                .willReturn(trainerToUpdate);

        // when
        Trainer actual = repository.saveOrUpdate(trainerToUpdate);

        // then
        assertThat(actual).isEqualTo(trainerToUpdate);

        verify(trainerToUpdate, never()).toBuilder();
        verify(storage, only()).put(id, trainerToUpdate);
    }

    @Test
    @DisplayName("Test delete trainer by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        Long id = 1L;

        doNothing().when(storage).remove(id, Trainer.class);

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id, Trainer.class);
    }
}