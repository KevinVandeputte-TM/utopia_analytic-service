package fact.it.utopia_visitservice.model;

import java.time.LocalDate;
import java.util.Date;

public class VisitDTO {
    private String id;
    private int stationID;
    private int interestID;
    private int count;

    public VisitDTO() {
    }

    public VisitDTO(String id, int stationID, int count, int interestID) {
        this.id = id;
        this.stationID = stationID;
        this.count = count;
        this.interestID = interestID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }
}
