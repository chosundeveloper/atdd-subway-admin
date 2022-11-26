package nextstep.subway.line.domain;

import nextstep.subway.station.domain.Station;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    @Embedded
    private Distance distance;

    protected Section() {}

    public Section(long id, Line line, Station upStation, Station downStation, Distance distance) {
        this.id = id;
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Section(Line line, Station upStation, Station downStation, Distance distance) {
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(id, section.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public boolean isDownStation(Station firstUpStation) {
        return this.downStation.equals(firstUpStation);
    }

    public boolean isUpStation(Station upStation) {
        return this.upStation.equals(upStation);
    }

    public Distance getDistance() {
        return this.distance;
    }

    public Line getLine() {
        return line;
    }

    public boolean isLonger(Section addSection) {
        return this.distance.compareTo(addSection.getDistance()) > 0;
    }

    public Distance differ(Section section) {
        return new Distance(this.distance.getDistance() - section.getDistance().getDistance());
    }

    public Distance sumDistance(Section section) {
        return new Distance(this.distance.sum(section.getDistance()));
    }

    public boolean isContainStation(Section section) {
        return section.isContainStation(this.upStation) || section.isContainStation(this.downStation);
    }

    private boolean isContainStation(Station station) {
        return isUpStation(station) || isDownStation(station);
    }
}
