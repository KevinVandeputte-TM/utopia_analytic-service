package fact.it.utopia_visitservice.model;

import java.time.LocalDate;
import java.util.Date;

public class VisitDTO {
    private int stationID;
    private int interestID;

    public VisitDTO() {
    }

    public VisitDTO(int stationID, int interestID) {
        this.stationID = stationID;
        this.interestID = interestID;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }
}
