package nextstep.subway.line;

import nextstep.subway.line.domain.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static nextstep.subway.line.domain.Color.NULL_AND_EMPTY_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("색상")
public class ColorTest {

    @DisplayName("색상 생성")
    @Test
    void constructor() {
        assertThatNoException().isThrownBy(() -> new Color("red"));
    }

    @DisplayName("null 이거나 empty 일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void null_empty(String color) {
        assertThatThrownBy(() -> new Color(color))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(NULL_AND_EMPTY_EXCEPTION_MESSAGE);
    }
}
