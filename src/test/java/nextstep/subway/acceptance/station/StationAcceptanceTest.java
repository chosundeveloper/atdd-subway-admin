package nextstep.subway.acceptance.station;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.acceptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static nextstep.subway.acceptance.station.StationSteps.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("지하철역 관련 기능")
public class StationAcceptanceTest extends AcceptanceTest {


    /**
     * When 지하철역을 생성하면
     * Then 지하철역이 생성된다
     * Then 지하철역 목록 조회 시 생성한 역을 찾을 수 있다
     */
    @DisplayName("지하철역을 생성한다.")
    @Test
    void createStation() {
        // when
        ExtractableResponse<Response> response = 지하철역_생성(GANGNAM_STATION);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // then
        List<String> stationNames = 지하철역_목록_조회().jsonPath().getList("name");
        assertThat(stationNames).containsAnyOf("강남역");
    }

    /**
     * Given 지하철역을 생성하고
     * When 기존에 존재하는 지하철역 이름으로 지하철역을 생성하면
     * Then 지하철역 생성이 안된다
     */
    @DisplayName("기존에 존재하는 지하철역 이름으로 지하철역을 생성한다.")
    @Test
    void createStationWithDuplicateName() {
        // given
        지하철역_생성(GANGNAM_STATION);

        // when
        ExtractableResponse<Response> response = 지하철역_생성(GANGNAM_STATION);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Given 2개의 지하철역을 생성하고
     * When 지하철역 목록을 조회하면
     * Then 2개의 지하철역을 응답 받는다
     */
    @DisplayName("지하철역 목록을 조회한다.")
    @Test
    void 지하철역_목록_조회_테스트() {
        // given
        지하철역_생성(GANGNAM_STATION);
        지하철역_생성(YUKSAM_STATION);

        // when
        List<String> stationNames = 지하철역_목록_조회().jsonPath().getList("name");

        // then
        assertAll(
                () -> assertThat(stationNames.size()).isEqualTo(2),
                () -> assertThat(stationNames).containsAnyOf(GANGNAM_STATION),
                () -> assertThat(stationNames).containsAnyOf(YUKSAM_STATION)
        );
    }


    /**
     * Given 지하철역을 생성하고
     * When 그 지하철역을 삭제하면
     * Then 그 지하철역 목록 조회 시 생성한 역을 찾을 수 없다
     */
    @DisplayName("지하철역을 제거한다.")
    @Test
    void deleteStation() {
        // Given
        ExtractableResponse<Response> createResponse = 지하철역_생성(GANGNAM_STATION);
        // When
        ExtractableResponse<Response> deleteResponse = 지하철역_삭제(createResponse.jsonPath().getLong("id"));
        // Then
        역_삭제_검증(deleteResponse, GANGNAM_STATION);
    }
}
