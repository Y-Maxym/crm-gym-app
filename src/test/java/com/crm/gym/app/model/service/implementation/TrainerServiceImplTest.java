package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.Trainer;
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
class TrainerServiceImplTest {

    @Mock
    private MessageUtils messageUtils;

    @Mock
    private EntityDao<Long, Trainer> repository;

    @InjectMocks
    private TrainerServiceImpl service;

    @Test
    @DisplayName("Test find trainer by id functionality")
    public void givenId_whenFindById_thenTrainerIsReturned() {
        // given
        Trainer expected = DataUtils.getTrainerEmilyDavis();
        Long id = expected.getId();

        given(repository.findById(anyLong()))
                .willReturn(Optional.of(expected));

        // when
        Trainer actual = service.findById(id);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainer by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        Long id = 1L;
        String message = "Trainer with id %s not found".formatted(id);

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
    @DisplayName("Test save trainer functionality")
    public void givenSaveTrainer_whenSave_thenRepositoryIsCalled() {
        // given
        Trainer trainer = DataUtils.getTrainerEmilyDavis();

        doNothing().when(repository).save(any(Trainer.class));

        // when
        service.save(trainer);

        // then
        verify(repository, only()).save(trainer);
    }

    @Test
    @DisplayName("Test update trainer functionality")
    public void givenUpdatedTrainer_whenUpdate_thenRepositoryIsCalled() {
        // given
        Trainer trainer = DataUtils.getTrainerEmilyDavis();

        doNothing().when(repository).update(any(Trainer.class));

        // when
        service.update(trainer);

        // then
        verify(repository, only()).update(trainer);
    }

}