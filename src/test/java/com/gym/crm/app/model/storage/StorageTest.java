package com.gym.crm.app.model.storage;

import com.gym.crm.app.exception.StorageNotFoundException;
import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.util.Constants;
import com.gym.crm.app.util.MessageUtils;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static com.gym.crm.app.util.Constants.ERROR_STORAGE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StorageTest {

    @Mock
    private MessageUtils messageUtils;

    @InjectMocks
    private Storage storage;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.invokeMethod(storage, "init");
    }

    @Test
    @DisplayName("Test get by id functionality")
    public void givenId_whenGet_thenEntityFound() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoePersisted();

        storage.put(expected.getId(), expected);

        // when
        Trainee actual = storage.get(expected.getId(), Trainee.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test get by incorrect id functionality")
    public void givenIncorrectId_whenGet_thenEntityNotFound() {
        // given
        Long id = 1L;

        // when
        Trainee actual = storage.get(id, Trainee.class);

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test get all functionality")
    public void givenEntities_whenGetAll_thenEntitiesFound() {
        // given
        Trainee trainee1 = DataUtils.getTraineeJohnDoePersisted();
        Trainee trainee2 = DataUtils.getTraineeJaneSmithPersisted();
        Trainee trainee3 = DataUtils.getTraineeMichaelJohnsonPersisted();

        storage.put(trainee1.getId(), trainee1);
        storage.put(trainee2.getId(), trainee2);
        storage.put(trainee3.getId(), trainee3);

        // when
        List<Trainee> actual = storage.getAll(Trainee.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(3);
        assertThat(actual).contains(trainee1, trainee2, trainee3);
    }

    @Test
    @DisplayName("Test get all not found functionality")
    public void givenNoEntities_whenGetAll_thenEntitiesNotFound() {
        // given

        // when
        List<Trainee> actual = storage.getAll(Trainee.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test put entity functionality")
    public void givenEntity_whenPut_thenEntityIsReturned() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoePersisted();

        // when
        storage.put(expected.getId(), expected);
        Trainee actual = storage.get(expected.getId(), Trainee.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test put entity functionality")
    public void givenId_whenRemove_thenSuccess() {
        // given
        Trainee expected = DataUtils.getTraineeJohnDoePersisted();
        storage.put(expected.getId(), expected);

        // when
        storage.remove(expected.getId(), Trainee.class);

        Trainee actual = storage.get(expected.getId(), Trainee.class);

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Test clear functionality")
    public void givenEntities_whenClear_thenSuccess() {
        // given
        Trainee trainee1 = DataUtils.getTraineeJohnDoePersisted();
        Trainee trainee2 = DataUtils.getTraineeJaneSmithPersisted();
        Trainee trainee3 = DataUtils.getTraineeMichaelJohnsonPersisted();

        storage.put(trainee1.getId(), trainee1);
        storage.put(trainee2.getId(), trainee2);
        storage.put(trainee3.getId(), trainee3);

        // when
        storage.clear(Trainee.class);

        List<Trainee> actual = storage.getAll(Trainee.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test define storage found functionality")
    public void givenClass_whenDefineStorage_thenStorageIsReturned() {
        // given
        Class<?> clazz = Trainee.class;

        // when
        Map<Long, Trainee> definedStorage = ReflectionTestUtils.invokeMethod(storage, "defineStorage", clazz);

        // then
        assertThat(definedStorage).isNotNull();
    }

    @Test
    @DisplayName("Test define storage not found functionality")
    public void givenClass_whenDefineStorage_thenStorageNotFound() {
        // given
        Class<?> clazz = Constants.class;

        given(messageUtils.getMessage(ERROR_STORAGE_NOT_FOUND, clazz))
                .willReturn("Storage for %s not found".formatted(clazz));
        // when
        StorageNotFoundException ex = assertThrows(StorageNotFoundException.class, () -> {
            ReflectionTestUtils.invokeMethod(storage, "defineStorage", clazz);
        });

        // then
        assertThat(ex.getMessage()).isEqualTo("Storage for %s not found".formatted(clazz));
    }
}