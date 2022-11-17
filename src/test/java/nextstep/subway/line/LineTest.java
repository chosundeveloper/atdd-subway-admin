package nextstep.subway.line;

import nextstep.subway.line.domain.Color;
import nextstep.subway.common.vo.ColorTest;
import nextstep.subway.common.domain.Name;
import nextstep.subway.line.domain.Line;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.common.vo.NameTest.신분당선_이름;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("노선")
class LineTest {

    public static final Line 신분당선 = new Line(1L, 신분당선_이름, ColorTest.신분당선_색상);

    @DisplayName("노선 생성")
    @Test
    void constructor() {
        assertThatNoException().isThrownBy(() -> new Line(신분당선_이름, ColorTest.신분당선_색상));
    }

    @DisplayName("노선 수정")
    @Test
    void update() {
        Line line = new Line(신분당선_이름, ColorTest.신분당선_색상);
        line.update(new Name("2호선"), new Color("green"));
        assertAll(
                () -> assertThat(line.getName()).isEqualTo(new Name("2호선")),
                () -> assertThat(line.getColor()).isEqualTo(new Color("green"))
        );
    }
}
