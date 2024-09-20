package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.utils.EntityTestData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TrainingDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TrainingDao repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test find training by id functionality")
    public void givenId_whenFindById_thenTrainingIsReturned() {
        // given
        Training expected = EntityTestData.getTransientTrainingEmilyDavis();
        repository.save(expected);

        // when
        Optional<Training> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find training by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        Training expected = EntityTestData.getPersistedTrainingDavidBrown();

        // when
        Optional<Training> actual = repository.findById(expected.getId());

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all training functionality")
    public void givenTrainings_whenFindAll_thenTrainingsIsReturned() {
        // given
        Training training1 = EntityTestData.getTransientTrainingEmilyDavis();
        Training training2 = EntityTestData.getTransientTrainingDavidBrown();

        repository.saveAll(training1, training2);

        // when
        List<Training> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).containsAll(List.of(training1, training2));
    }

    @Test
    @DisplayName("Test save training without id functionality")
    public void givenTraining_whenSaveTraining_thenTrainingIsSaved() {
        // given
        Training training = EntityTestData.getTransientTrainingEmilyDavis();

        // when
        Training actual = repository.save(training);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test save training with id functionality")
    public void givenTrainingWithId_whenSaveTraining_thenExceptionIsThrown() {
        // given
        Training training = EntityTestData.getPersistedTrainingEmilyDavis();

        // when
        InvalidDataAccessApiUsageException ex = assertThrows(InvalidDataAccessApiUsageException.class, () -> repository.save(training));

        // then
        assertThat(ex).hasCauseInstanceOf(PersistentObjectException.class);
    }

    @Test
    @DisplayName("Test update training functionality")
    public void givenTraining_whenUpdateTraining_thenTrainingIsUpdated() {
        // given
        Training training = EntityTestData.getTransientTrainingEmilyDavis();
        repository.save(training);

        // when

        Training actual = repository.update(training);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("Test delete training by id functionality")
    public void givenId_whenDeleteById_thenTrainingIsDeleted() {
        // given
        Training training = EntityTestData.getTransientTrainingEmilyDavis();
        repository.save(training);

        // when
        repository.deleteById(training.getId());

        entityManager.clear();

        Optional<Training> actual = repository.findById(training.getId());

        // then
        assertThat(actual.isEmpty()).isTrue();
    }
}
