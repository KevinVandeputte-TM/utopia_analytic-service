package fact.it.utopia_visitservice.model;

import java.time.LocalDate;
import java.util.List;

public class VisitGrouped {
    private int stationID;
    private int total;
    private LocalDate date;
    private List<InterestCount> countPerInterest;

    public VisitGrouped() {
    }

    public VisitGrouped(int stationID, int total, LocalDate date, List<InterestCount> countPerInterest) {
        this.stationID = stationID;
        this.total = total;
        this.date = date;
        this.countPerInterest = countPerInterest;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<InterestCount> getCountPerInterest() {
        return countPerInterest;
    }

    public void setCountPerInterest(List<InterestCount> countPerInterest) {
        this.countPerInterest = countPerInterest;
    }
}
