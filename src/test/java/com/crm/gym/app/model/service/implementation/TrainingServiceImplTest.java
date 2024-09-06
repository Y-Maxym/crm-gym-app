package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.util.MessageUtils;
import com.crm.gym.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private MessageUtils messageUtils;

    @Mock
    private EntityDao<Long, Training> repository;

    @InjectMocks
    private TrainingServiceImpl service;

    @Test
    @DisplayName("Test find training by id functionality")
    public void givenId_whenFindById_thenTrainingIsReturned() {
        // given
        Training expected = DataUtils.getTrainingEmilyDavis();
        Long id = expected.getId();

        BDDMockito.given(repository.findById(anyLong()))
                .willReturn(Optional.of(expected));

        // when
        Training actual = service.findById(id);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find training by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        Long id = 1L;
        String message = "Training with id %s not found".formatted(id);

        BDDMockito.given(repository.findById(anyLong()))
                .willReturn(Optional.empty());

        BDDMockito.given(messageUtils.getMessage(anyString(), any()))
                .willReturn(message);

        // when
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test save training functionality")
    public void givenSaveTraining_whenSave_thenRepositoryIsCalled() {
        // given
        Training training = DataUtils.getTrainingEmilyDavis();

        BDDMockito.doNothing().when(repository).save(any(Training.class));

        // when
        service.save(training);

        // then
        verify(repository, only()).save(training);
    }

}