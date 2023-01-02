package fact.it.utopia_visitservice.controller;

import fact.it.utopia_visitservice.model.Visit;
import fact.it.utopia_visitservice.model.VisitDTO;
import fact.it.utopia_visitservice.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class VisitController {
    @Autowired
    private VisitRepository visitRepository;

    public VisitController(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @GetMapping("/visits")
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @GetMapping("/visits/{stationID}")
    public List<Visit> getVisitsForStation(@PathVariable int stationID) {
        return visitRepository.findByStationID(stationID);
    }

    @GetMapping("/visits/{interestID}")
    public List<Visit> getVisitsForInterest(@PathVariable int interestID) {
        return visitRepository.findByInterestID(interestID);
    }

    @GetMapping("/visits/{date}")
    public List<Visit> getVisitsForDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return visitRepository.findByDate(localDate);
    }

    @GetMapping("/visits/total")
    public List<Visit> getTotalPerStation() {
        List<Visit> visits = visitRepository.findAll();
        Map<Integer, List<Visit>> visitsGrouped =
                visits.stream().collect(Collectors.groupingBy(w -> w.getStationID()));

        List<Visit> visitsPerStation = new ArrayList<>();

        for (Map.Entry<Integer, List<Visit>> visitsOfStation : visitsGrouped.entrySet()) {
            Visit visit = new Visit();
            visit.setStationID(visitsOfStation.getKey());
            int sum = visitsOfStation.getValue().stream().mapToInt(o -> o.getCount()).sum();
            visit.setCount(sum);

            visitsPerStation.add(visit);
        }

        return visitsPerStation;
    }

    @PutMapping("/visit")
    public ResponseEntity<Void> createOrUpdate(@RequestBody VisitDTO visit) {
        Optional<Visit> visitOptional = Optional.ofNullable(visitRepository.findVisitByDateAndStationID(LocalDate.now(), visit.getStationID()));
        if (visitOptional.isPresent()) {
            Visit v = visitOptional.get();
            v.setCount(v.getCount() + 1);
            visitRepository.save(v);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        Visit v = new Visit();
        v.setCount(1);
        v.setStationID(visit.getStationID());
        v.setInterestID(visit.getInterestID());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
