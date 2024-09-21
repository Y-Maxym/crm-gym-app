package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.impl.TraineeRepositoryImpl;
import com.gym.crm.app.service.common.EntityValidator;
import com.gym.crm.app.utils.EntityTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.WARN_TRAINEE_WITH_ID_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private MessageHelper messageHelper;

    @Mock
    private EntityValidator entityValidator;

    @Mock
    private TraineeRepositoryImpl repository;

    @InjectMocks
    private TraineeServiceImpl service;

    @Test
    @DisplayName("Test find trainee by id functionality")
    public void givenId_whenFindById_thenTraineeIsReturned() {
        // given
        Trainee expected = EntityTestData.getPersistedTraineeJohnDoe();
        long id = expected.getId();

        doNothing().when(entityValidator).checkId(id);

        given(repository.findById(id))
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
        long id = 1L;
        String message = "Trainee with id %s not found".formatted(id);

        doNothing().when(entityValidator).checkId(id);

        given(repository.findById(id))
                .willReturn(Optional.empty());

        given(messageHelper.getMessage(ERROR_TRAINEE_WITH_ID_NOT_FOUND, id))
                .willReturn(message);

        // when
        EntityValidationException ex = assertThrows(EntityValidationException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test save trainee functionality")
    public void givenSaveTrainee_whenSave_thenRepositoryIsCalled() {
        // given
        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();

        doNothing().when(entityValidator).checkEntity(trainee);

        // when
        service.save(trainee);

        // then
        verify(repository, only()).save(trainee);
    }

    @Test
    @DisplayName("Test update trainee functionality")
    public void givenUpdatedTrainee_whenUpdate_thenRepositoryIsCalled() {
        // given
        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();

        doNothing().when(entityValidator).checkEntity(trainee);
        doNothing().when(entityValidator).checkId(trainee.getId());

        // when
        service.update(trainee);

        // then
        verify(repository, only()).update(trainee);
    }

    @Test
    @DisplayName("Test delete trainee by id functionality")
    public void givenId_whenDeleteById_thenRepositoryIsCalled() {
        // given
        long id = 1L;

        doNothing().when(entityValidator).checkId(id);
        doNothing().when(repository).deleteById(id);

        given(repository.findById(id))
                .willReturn(Optional.of(EntityTestData.getPersistedTraineeJohnDoe()));

        // when
        service.deleteById(id);

        // then
        verify(messageHelper, never()).getMessage(WARN_TRAINEE_WITH_ID_NOT_FOUND, id);
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete trainee by incorrect id functionality")
    public void givenIncorrectId_whenDeleteById_thenLogWarnIsCalled() {
        // given
        long id = 1L;

        doNothing().when(entityValidator).checkId(id);

        given(repository.findById(id))
                .willReturn(Optional.empty());

        // when
        service.deleteById(id);

        // then
        verify(messageHelper).getMessage(WARN_TRAINEE_WITH_ID_NOT_FOUND, id);
        verify(repository).deleteById(id);
    }
}
