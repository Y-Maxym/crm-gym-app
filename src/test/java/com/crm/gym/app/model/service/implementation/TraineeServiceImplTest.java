package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.util.MessageUtils;
import com.crm.gym.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private MessageUtils messageUtils;

    @Mock
    private EntityDao<Long, Trainee> repository;

    @InjectMocks
    private TraineeServiceImpl service;

    @Test
    @DisplayName("Test find trainee by id functionality")
    public void givenId_whenFindById_thenTraineeIsReturned() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoe();
        Long id = expected.getId();

        given(repository.findById(anyLong()))
                .willReturn(Optional.of(expected));

        // when
        Trainee actual = service.findById(id);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainee by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        Long id = 1L;
        String message = "Trainee with id %s not found".formatted(id);

        given(repository.findById(anyLong()))
                .willReturn(Optional.empty());

        given(messageUtils.getMessage(anyString(), any()))
                .willReturn(message);

        // when
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test save trainee functionality")
    public void givenSaveTrainee_whenSave_thenRepositoryIsCalled() {
        // given
        Trainee trainee = DataUtils.getTraineeJohnDoe();

        doNothing().when(repository).save(any(Trainee.class));

        // when
        service.save(trainee);

        // then
        verify(repository, only()).save(trainee);
    }

    @Test
    @DisplayName("Test update trainee functionality")
    public void givenUpdatedTrainee_whenUpdate_thenRepositoryIsCalled() {
        // given
        Trainee trainee = DataUtils.getTraineeJohnDoe();

        doNothing().when(repository).update(any(Trainee.class));

        // when
        service.update(trainee);

        // then
        verify(repository, only()).update(trainee);
    }

    @Test
    @DisplayName("Test delete trainee by id functionality")
    public void givenId_whenDeleteById_thenRepositoryIsCalled() {
        // given
        Long id = 1L;

        doNothing().when(repository).deleteById(anyLong());

        // when
        service.deleteById(id);

        // then
        verify(repository, only()).deleteById(id);
    }

}