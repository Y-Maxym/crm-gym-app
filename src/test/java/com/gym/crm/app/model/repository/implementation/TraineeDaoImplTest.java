package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.Trainee;
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
class TraineeDaoImplTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private TraineeDaoImpl repository;

    @Test
    @DisplayName("Test find trainee by id functionality")
    public void givenId_whenFindById_thenTraineeIsReturned() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoePersisted();
        Long id = expected.getId();

        given(storage.get(id, Trainee.class))
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

        given(storage.get(id, Trainee.class))
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
        Trainee trainee1 = DataUtils.getTraineeJohnDoePersisted();
        Trainee trainee2 = DataUtils.getTraineeJaneSmithPersisted();
        Trainee trainee3 = DataUtils.getTraineeMichaelJohnsonPersisted();

        List<Trainee> expected = List.of(trainee1, trainee2, trainee3);

        given(storage.getAll(Trainee.class))
                .willReturn(expected);

        // when
        List<Trainee> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save trainee functionality")
    public void givenTraineeToSave_whenSaveOrUpdateTrainee_thenStorageIsCalled() {
        // given
        Trainee traineeToSave = spy(DataUtils.getTraineeJohnDoeTransient());
        Trainee persisted = DataUtils.getTraineeJohnDoePersisted();

        given(storage.put(anyLong(), eq(persisted)))
                .willReturn(persisted);

        // when
        Trainee actual = repository.saveOrUpdate(traineeToSave);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isEqualTo(persisted);

        verify(traineeToSave, times(1)).toBuilder();
        verify(storage, only()).put(anyLong(), eq(persisted));
    }

    @Test
    @DisplayName("Test update trainee functionality")
    public void givenTraineeToUpdate_whenSaveOrUpdateTrainee_thenStorageIsCalled() {
        // given
        Trainee traineeToUpdate = spy(DataUtils.getTraineeJohnDoePersisted());
        Long id = traineeToUpdate.getId();

        given(storage.put(id, traineeToUpdate))
                .willReturn(traineeToUpdate);

        // when
        Trainee actual = repository.saveOrUpdate(traineeToUpdate);

        // then
        assertThat(actual).isEqualTo(traineeToUpdate);

        verify(traineeToUpdate, never()).toBuilder();
        verify(storage, only()).put(id, traineeToUpdate);
    }

    @Test
    @DisplayName("Test delete trainee by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        Long id = 1L;

        doNothing().when(storage).remove(id, Trainee.class);

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id, Trainee.class);
    }
}