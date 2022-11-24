package nextstep.subway.acceptance.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.acceptance.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static nextstep.subway.acceptance.line.LineSteps.*;
import static nextstep.subway.acceptance.station.StationSteps.*;
import static nextstep.subway.line.LineFixture.신분당선_색상;
import static nextstep.subway.line.LineFixture.신분당선_이름;
import static nextstep.subway.line.SectionTest.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("노선 관련 기능")
class SectionAcceptanceTest extends AcceptanceTest {
//    - [ ] 상행역을 기준으로 구간을 추가한다.
//            - [ ] 새로운 구간의 거리가 기존 구간의 거리보다 크거나 같으면 등록을 할 수 없다.

    private Long 논현역_ID;
    private Long 신논현역_ID;
    private Long 강남역_ID;
    private Long 역삼역_ID;

    @BeforeEach
    public void setUp() {
        super.setUp();
        논현역_ID = 지하철역_생성(NONHYUN_STATION).jsonPath().getLong("id");
        신논현역_ID = 지하철역_생성(SHINNONHYUN_STATION).jsonPath().getLong("id");
        강남역_ID = 지하철역_생성(GANGNAM_STATION).jsonPath().getLong("id");
        역삼역_ID = 지하철역_생성(YUKSAM_STATION).jsonPath().getLong("id");
    }

    /**
     * When 2개의 지하철 역이 생성되어 있다.
     * When 지하철 노선이 생성되어 있다.
     * Then
     */
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createStation() {

        //when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(신분당선_이름, 신분당선_색상, 논현역_ID, 신논현역_ID, 논현역_신논현역_거리);

        //then
        지하철_노선_생성_검증(response);
    }

    /**
     * given 추가할 구간의 길이와 같은 기존의 구간을 생성한다.
     * When 기존 구간 사이에 지하철 노선 생성을 요청하면
     * Then 구간 추가에 실패한다.
     */
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void addSection_distance() {

        //given
        ExtractableResponse<Response> createLineResponse = 지하철_노선_생성_요청(신분당선_이름, 신분당선_색상, 논현역_ID, 강남역_ID, 논현역_강남역_거리);

        //when
        ExtractableResponse<Response> response = 지하철_노선에_지하철_구간_생성_요청(createLineResponse.jsonPath().getLong("id"), createSectionCreateParams(신논현역_ID, 강남역_ID, 신논현역_강남역_거리));

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private Map<String, String> createSectionCreateParams(Long upStationId, Long downStationId, int distance) {
        Map<String, String> params = new HashMap<>();
        params.put("upStationId", upStationId + "");
        params.put("downStationId", downStationId + "");
        params.put("distance", distance + "");
        return params;
    }
}