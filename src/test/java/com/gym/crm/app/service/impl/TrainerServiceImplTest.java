package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.impl.TrainerRepositoryImpl;
import com.gym.crm.app.service.common.EntityValidator;
import com.gym.crm.app.utils.EntityTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_ID_NOT_FOUND;
import static com.gym.crm.app.util.Constants.ERROR_TRAINER_WITH_USERNAME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private MessageHelper messageHelper;

    @Mock
    private EntityValidator entityValidator;

    @Mock
    private TrainerRepositoryImpl repository;

    @InjectMocks
    private TrainerServiceImpl service;

    @Test
    @DisplayName("Test find trainer by id functionality")
    public void givenId_whenFindById_thenTrainerIsReturned() {
        // given
        Trainer expected = EntityTestData.getPersistedTrainerEmilyDavis();
        long id = expected.getId();

        doNothing().when(entityValidator).checkId(id);
        given(repository.findById(id))
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
        long id = 1L;
        String message = "Trainer with id %s not found".formatted(id);

        doNothing().when(entityValidator).checkId(id);
        given(repository.findById(id))
                .willReturn(Optional.empty());
        given(messageHelper.getMessage(ERROR_TRAINER_WITH_ID_NOT_FOUND, id))
                .willReturn(message);

        // when
        EntityValidationException ex = assertThrows(EntityValidationException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test find trainer by username functionality")
    public void givenUsername_whenFindByUsername_thenTrainerIsReturned() {
        // given
        Trainer expected = EntityTestData.getPersistedTrainerEmilyDavis();
        String username = expected.getUser().getUsername();

        given(repository.findByUsername(username))
                .willReturn(Optional.of(expected));

        // when
        Trainer actual = service.findByUsername(username);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainer by incorrect username functionality")
    public void givenIncorrectUsername_whenFindByUsername_thenExceptionIsThrown() {
        // given
        String username = "username";
        String message = "Trainer with username %s not found".formatted(username);

        given(repository.findByUsername(username))
                .willReturn(Optional.empty());
        given(messageHelper.getMessage(ERROR_TRAINER_WITH_USERNAME_NOT_FOUND, username))
                .willReturn(message);

        // when
        EntityValidationException ex = assertThrows(EntityValidationException.class, () -> service.findByUsername(username));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test find trainings by criteria functionality")
    public void givenCriteria_whenFindByCriteria_thenRepositoryIsCalled() {
        // given
        String username = "username";
        LocalDate from = LocalDate.parse("2020-01-01");
        LocalDate to = LocalDate.parse("2020-01-01");
        String trainee = "trainee";
        String trainingType = "trainingType";

        // when
        service.findTrainingsByCriteria(username, from, to, trainee, trainingType);

        // then
        verify(repository).findTrainingsByCriteria(username, from, to, trainee, trainingType);
    }

    @Test
    @DisplayName("Test save trainer functionality")
    public void givenSaveTrainer_whenSave_thenRepositoryIsCalled() {
        // given
        Trainer trainer = EntityTestData.getPersistedTrainerEmilyDavis();

        doNothing().when(entityValidator).checkEntity(trainer);

        // when
        service.save(trainer);

        // then
        verify(repository, only()).save(trainer);
    }

    @Test
    @DisplayName("Test update trainer functionality")
    public void givenUpdatedTrainer_whenUpdate_thenRepositoryIsCalled() {
        // given
        Trainer trainer = EntityTestData.getPersistedTrainerEmilyDavis();

        doNothing().when(entityValidator).checkEntity(trainer);

        // when
        service.update(trainer);

        // then
        verify(repository, only()).update(trainer);
    }

    @Test
    @DisplayName("Test find trainer by incorrect id functionality")
    public void givenUsername_whenGtTrainersNotAssigned_thenTrainersIsReturned() {
        // given
        String username = "username";

        Trainer trainer1 = EntityTestData.getPersistedTrainerEmilyDavis();
        Trainer trainer2 = EntityTestData.getTransientTrainerDavidBrown();

        List<Trainer> trainers = List.of(trainer1, trainer2);

        doNothing().when(entityValidator).checkEntity(username);
        given(repository.getTrainersNotAssignedByTraineeUsername(username))
                .willReturn(trainers);

        // when
        List<Trainer> actual = service.getTrainersNotAssignedByTraineeUsername(username);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(trainers);
    }
}
