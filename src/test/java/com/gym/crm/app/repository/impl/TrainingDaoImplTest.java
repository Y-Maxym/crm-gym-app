package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.storage.Storage;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class TrainingDaoImplTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private TrainingDaoImpl repository;

    @Test
    @DisplayName("Test find training by id functionality")
    public void givenId_whenFindById_thenTrainingIsReturned() {
        // given
        Training expected = DataUtils.getTrainingEmilyDavisPersisted();
        Long id = expected.getId();

        given(storage.get(id, Training.class))
                .willReturn(expected);

        // when
        Optional<Training> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find training by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        Long id = 1L;

        given(storage.get(id, Training.class))
                .willReturn(null);

        // when
        Optional<Training> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all training functionality")
    public void givenTrainings_whenFindAll_thenTrainingsIsReturned() {
        // given
        Training training1 = DataUtils.getTrainingDavidBrownPersisted();
        Training training2 = DataUtils.getTrainingEmilyDavisPersisted();

        List<Training> expected = List.of(training1, training2);

        given(storage.getAll(Training.class))
                .willReturn(expected);

        // when
        List<Training> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save training without id functionality")
    public void givenTraining_whenSaveTraining_thenStorageIsCalled() {
        // given
        Training training = spy(DataUtils.getTrainingEmilyDavisTransient());
        Training persisted = DataUtils.getTrainingEmilyDavisPersisted();

        given(storage.put(anyLong(), any(Training.class)))
                .willReturn(persisted);

        // when
        Training actual = repository.save(training);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isEqualTo(persisted);

        verify(training, times(1)).toBuilder();
        verify(storage, only()).put(anyLong(), any(Training.class));
    }

    @Test
    @DisplayName("Test save training with id functionality")
    public void givenTrainingWithId_whenSaveTraining_thenStorageIsCalled() {
        // given
        Training training = spy(DataUtils.getTrainingDavidBrownPersisted());
        Long id = training.getId();

        given(storage.put(id, training))
                .willReturn(training);

        // when
        Training actual = repository.save(training);

        // then
        assertThat(actual).isEqualTo(training);

        verify(training, never()).toBuilder();
        verify(storage, only()).put(id, training);
    }

    @Test
    @DisplayName("Test update training functionality")
    public void givenTraining_whenUpdateTraining_thenStorageIsCalled() {
        // given
        Training training = spy(DataUtils.getTrainingDavidBrownPersisted());
        Long id = training.getId();

        given(storage.put(id, training))
                .willReturn(training);

        // when
        Training actual = repository.update(training);

        // then
        assertThat(actual).isEqualTo(training);

        verify(storage, only()).put(id, training);
    }

    @Test
    @DisplayName("Test delete training by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        Long id = 1L;

        doNothing().when(storage).remove(id, Training.class);

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id, Training.class);
    }
}
