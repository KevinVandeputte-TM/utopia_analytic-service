package fact.it.utopia_visitservice.repository;

import fact.it.utopia_visitservice.model.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface VisitRepository extends MongoRepository<Visit, String> {
    List<Visit> findByStationID(int stationID);
    List<Visit> findByInterestID(int interestID);
    List<Visit> findByDate(LocalDate date);
    Visit findVisitByDateAndStationID(LocalDate date, int stationID);
}
