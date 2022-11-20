package nextstep.subway.line;

import nextstep.subway.line.domain.Sections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.line.SectionFixture.*;
import static nextstep.subway.line.domain.Sections.*;
import static nextstep.subway.station.domain.StationFixtrue.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("구간들")
class SectionsTest {

    @DisplayName("구간 추가")
    @Test
    void add() {
        Sections sections = new Sections();
        sections.add(논현역_신논현역_구간());
        sections.add(신논현역_강남역_구간());
        assertThat(sections.size()).isEqualTo(2);
    }

    @DisplayName("구간역 목록을 조회한다.")
    @Test
    void findStations() {
        Sections sections = new Sections();
        sections.add(논현역_신논현역_구간());
        sections.add(신논현역_강남역_구간());
        assertThat(sections.getStations()).containsExactly(논현역(), 신논현역(), 강남역());
    }
    
    @DisplayName("상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가할 수 없다.")
    @Test
    void duplicate() {
        Sections sections = new Sections();
        sections.add(논현역_신논현역_구간());
        sections.add(신논현역_강남역_구간());
        assertThatThrownBy(() -> sections.add(신논현역_강남역_구간()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(SECTION_DUPLICATE_EXCEPTION_MESSAGE);
    }

    @DisplayName("상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없다.")
    @Test
    void contains() {
        Sections sections = new Sections();
        sections.add(논현역_신논현역_구간());
        assertThatThrownBy(() -> sections.add(강남역_역삼역_구간()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(SECTION_CONTAINS_EXCEPTION_MESSAGE);
    }

    @DisplayName("새로운 구간의 거리가 기존 구간의 거리보다 크거나 같으면 등록을 할 수 없다.")
    @Test
    void distance() {
        Sections sections = new Sections();
        sections.add(논현역_강남역_구간());
        assertThatThrownBy(() -> sections.add(신논현역_강남역_구간()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(DISTANCE_MINIMUM_EXCEPTION_MESSAGE);
    }
}
