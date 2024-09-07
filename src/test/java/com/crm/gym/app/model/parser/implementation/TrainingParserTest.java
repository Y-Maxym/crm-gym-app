package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.exception.ParseException;
import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.util.ParseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TrainingParserTest {

    @Mock
    private ParseUtils parseUtils;

    @InjectMocks
    private TrainingParser trainingParser;

    @Test
    @DisplayName("Test parse with correct data functionality")
    public void givenCorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "1,1,1,Training Emily Davis,1,2020-01-01T10:00:00,2";

        Long trainingId = 1L;
        Long traineeId = 1L;
        Long trainerId = 1L;
        String trainingName = "Training Emily Davis";
        Long trainingTypeId = 1L;
        LocalDateTime trainingDate = LocalDateTime.of(2020, 1, 1, 10, 0, 0);
        int trainingDurationInHours = 2;
        Duration trainingDuration = Duration.ofHours(trainingDurationInHours);

        given(parseUtils.parseLong(trainingId.toString()))
                .willReturn(trainingId);

        given(parseUtils.parseLong(traineeId.toString()))
                .willReturn(traineeId);

        given(parseUtils.parseLong(trainerId.toString()))
                .willReturn(trainerId);

        given(parseUtils.parseLong(trainingTypeId.toString()))
                .willReturn(trainingTypeId);

        given(parseUtils.parseDateTime(anyString()))
                .willReturn(trainingDate);

        given(parseUtils.parseInt(Integer.toString(trainingDurationInHours)))
                .willReturn(trainingDurationInHours);
        // when
        Training actual = trainingParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(trainingId);
        assertThat(actual.getTraineeId()).isEqualTo(traineeId);
        assertThat(actual.getTrainerId()).isEqualTo(trainerId);
        assertThat(actual.getTrainingName()).isEqualTo(trainingName);
        assertThat(actual.getTrainingTypeId()).isEqualTo(trainingTypeId);
        assertThat(actual.getTrainingDate()).isEqualTo(trainingDate);
        assertThat(actual.getTrainingDuration()).isEqualTo(trainingDuration);
    }

    @Test
    @DisplayName("Test parse with missing values functionality")
    public void givenMissingValuesInput_whenParse_thenNullValuesIsReturned() {
        // given
        String input = "1,,,Training Emily Davis,1,,2";

        Long trainingId = 1L;
        String trainingName = "Training Emily Davis";
        Long trainingTypeId = 1L;
        int trainingDurationInHours = 2;
        Duration trainingDuration = Duration.ofHours(trainingDurationInHours);

        given(parseUtils.parseLong(trainingId.toString()))
                .willReturn(trainingId);

        given(parseUtils.parseLong(trainingTypeId.toString()))
                .willReturn(trainingTypeId);


        given(parseUtils.parseInt(Integer.toString(trainingDurationInHours)))
                .willReturn(trainingDurationInHours);

        // when
        Training actual = trainingParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(trainingId);
        assertThat(actual.getTraineeId()).isNull();
        assertThat(actual.getTrainerId()).isNull();
        assertThat(actual.getTrainingName()).isEqualTo(trainingName);
        assertThat(actual.getTrainingTypeId()).isEqualTo(trainingTypeId);
        assertThat(actual.getTrainingDate()).isNull();
        assertThat(actual.getTrainingDuration()).isEqualTo(trainingDuration);
    }

    @Test
    @DisplayName("Test parse with incorrect data functionality")
    public void givenIncorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "1,1,1,Training Emily Davis,1,date,2";

        String trainingDate = "date";

        given(parseUtils.parseDateTime(trainingDate))
                .willThrow(new ParseException("DateTime is not in a valid format: %s".formatted(trainingDate)));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> trainingParser.parse(input));

        // then
        assertThat(ex.getMessage()).isEqualTo("DateTime is not in a valid format: %s".formatted(trainingDate));
    }

    @Test
    @DisplayName("Test parse with empty input data functionality")
    public void givenEmptyInput_whenParse_thenEmptyTrainingIsReturned() {
        // given
        String input = "";

        // when
        Training actual = trainingParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getTraineeId()).isNull();
        assertThat(actual.getTrainerId()).isNull();
        assertThat(actual.getTrainingName()).isNull();
        assertThat(actual.getTrainingTypeId()).isNull();
        assertThat(actual.getTrainingDate()).isNull();
        assertThat(actual.getTrainingDuration()).isNull();
    }

}