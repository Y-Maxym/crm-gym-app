package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.storage.implementation.TraineeStorage;
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
class TraineeDaoImplTest {

    @Mock
    private TraineeStorage storage;

    @InjectMocks
    private TraineeDaoImpl repository;

    @Test
    @DisplayName("Test find trainee by id functionality")
    public void givenId_whenFindById_thenTraineeIsReturned() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoe();
        Long id = expected.getId();

        given(storage.get(id))
                .willReturn(expected);

        // when
        Optional<Trainee> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainee by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        Long id = 1L;

        given(storage.get(id))
                .willReturn(null);

        // when
        Optional<Trainee> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all trainee functionality")
    public void givenTrainees_whenFindAll_thenTraineesIsReturned() {
        // given
        Trainee trainee1 = DataUtils.getTraineeJohnDoe();
        Trainee trainee2 = DataUtils.getTraineeJaneSmith();
        Trainee trainee3 = DataUtils.getTraineeMichaelJohnson();

        List<Trainee> expected = List.of(trainee1, trainee2, trainee3);

        given(storage.getAll())
                .willReturn(expected);

        // when
        List<Trainee> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save trainee functionality")
    public void givenTraineeToSave_whenSaveTrainee_thenStorageIsCalled() {
        // given
        Trainee traineeToSave = DataUtils.getTraineeJohnDoe();

        given(storage.put(traineeToSave))
                .willReturn(traineeToSave);

        // when
        Trainee actual = repository.save(traineeToSave);

        // then
        assertThat(actual).isEqualTo(traineeToSave);

        verify(storage, only()).put(traineeToSave);
    }

    @Test
    @DisplayName("Test update trainee functionality")
    public void givenTraineeToUpdate_whenSaveTrainee_thenStorageIsCalled() {
        // given
        Trainee traineeToUpdate = DataUtils.getTraineeJohnDoe();

        given(storage.put(traineeToUpdate))
                .willReturn(traineeToUpdate);

        // when
        Trainee actual = repository.update(traineeToUpdate);

        // then
        assertThat(actual).isEqualTo(traineeToUpdate);

        verify(storage, only()).put(traineeToUpdate);
    }

    @Test
    @DisplayName("Test delete trainee by id functionality")
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