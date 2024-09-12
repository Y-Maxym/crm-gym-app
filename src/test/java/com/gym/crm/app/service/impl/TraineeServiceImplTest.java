package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.EntityDao;
import com.gym.crm.app.service.EntityValidator;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gym.crm.app.util.Constants.ERROR_TRAINEE_WITH_ID_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private MessageHelper messageHelper;

    @Mock
    private EntityValidator exceptionHelper;

    @Mock
    private EntityDao<Long, Trainee> repository;

    @InjectMocks
    private TraineeServiceImpl service;

    @Test
    @DisplayName("Test find trainee by id functionality")
    public void givenId_whenFindById_thenTraineeIsReturned() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoePersisted();
        Long id = expected.getId();

        doNothing().when(exceptionHelper).checkId(id);

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
        Long id = 1L;
        String message = "Trainee with id %s not found".formatted(id);

        doNothing().when(exceptionHelper).checkId(id);

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
        Trainee trainee = DataUtils.getTraineeJohnDoePersisted();

        doNothing().when(exceptionHelper).checkEntity(trainee);

        // when
        service.save(trainee);

        // then
        verify(repository, only()).save(trainee);
    }

    @Test
    @DisplayName("Test update trainee functionality")
    public void givenUpdatedTrainee_whenUpdate_thenRepositoryIsCalled() {
        // given
        Trainee trainee = DataUtils.getTraineeJohnDoePersisted();

        doNothing().when(exceptionHelper).checkEntity(trainee);
        doNothing().when(exceptionHelper).checkId(trainee.getId());

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

        doNothing().when(exceptionHelper).checkId(id);
        doNothing().when(repository).deleteById(id);

        // when
        service.deleteById(id);

        // then
        verify(repository, only()).deleteById(id);
    }
}
