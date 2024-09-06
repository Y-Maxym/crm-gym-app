package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.storage.implementation.TrainingStorage;
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
class TrainingDaoImplTest {

    @Mock
    private TrainingStorage storage;

    @InjectMocks
    private TrainingDaoImpl repository;

    @Test
    @DisplayName("Test find training by id functionality")
    public void givenId_whenFindById_thenTrainingIsReturned() {
        // given
        Long id = 1L;
        Training expected = DataUtils.getTrainingEmilyDavis();

        BDDMockito.given(storage.get(id))
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

        BDDMockito.given(storage.get(id))
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
        Training training1 = DataUtils.getTrainingDavidBrown();
        Training training2 = DataUtils.getTrainingEmilyDavis();

        List<Training> expected = List.of(training1, training2);

        BDDMockito.given(storage.getAll())
                .willReturn(expected);

        // when
        List<Training> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save training functionality")
    public void givenTrainingToSave_whenSaveTraining_thenStorageIsCalled() {
        // given
        Training trainingToSave = DataUtils.getTrainingDavidBrown();

        BDDMockito.given(storage.put(anyLong(), any(Training.class)))
                .willReturn(null);

        // when
        repository.save(trainingToSave);

        // then
        verify(storage, only()).put(trainingToSave.getId(), trainingToSave);
    }

    @Test
    @DisplayName("Test update training functionality")
    public void givenTrainingToUpdate_whenSaveTraining_thenStorageIsCalled() {
        // given
        Training trainingToUpdate = DataUtils.getTrainingDavidBrown();
        Training previous = DataUtils.getTrainingEmilyDavis();

        BDDMockito.given(storage.put(anyLong(), any(Training.class)))
                .willReturn(previous);

        // when
        repository.save(trainingToUpdate);

        // then
        verify(storage, only()).put(trainingToUpdate.getId(), trainingToUpdate);
    }

    @Test
    @DisplayName("Test delete training by id functionality")
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