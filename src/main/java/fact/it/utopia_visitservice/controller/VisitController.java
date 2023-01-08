package fact.it.utopia_visitservice.controller;

import fact.it.utopia_visitservice.model.Visit;
import fact.it.utopia_visitservice.model.VisitDTO;
import fact.it.utopia_visitservice.repository.VisitRepository;
import jakarta.annotation.PostConstruct;
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

    private Random rnd = new Random();
    @PostConstruct
    public void fillDBtemp() {
        if(visitRepository.count() == 0) {
            for(int x = 0; x <= 7; x++) {
                for(int i = 9; i < 37; i++) {
                    for(int j = 1; j<=9; j++) {
                        Visit v = new Visit();
                        v.setStationID(i);
                        v.setCount(rnd.nextInt(30));
                        v.setDate(LocalDate.now().minusDays(x));
                        v.setInterestID(j);
                        visitRepository.save(v);
                    }
                }
            }

        }
    }

    @GetMapping("/visits")
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @GetMapping("/visits/{stationID}")
    public List<Visit> getVisitsForStation(@PathVariable int stationID) {
        return visitRepository.findByStationID(stationID);
    }

    @GetMapping("/visits/date/{date}")
    public List<Visit> getVisitsForDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return visitRepository.findByDate(localDate);
    }

    @PutMapping("/visit")
    public ResponseEntity<Void> createOrUpdate(@RequestBody VisitDTO visit) {
        Optional<Visit> visitOptional = Optional.ofNullable(visitRepository.findVisitByDateAndStationIDAndInterestID(LocalDate.now(), visit.getStationID(), visit.getInterestID()));
        if (visitOptional.isPresent()) {
            Visit v = visitOptional.get();
            v.incrementCount();
            visitRepository.save(v);
        } else {
            Visit v = new Visit();
            v.setDate(LocalDate.now());
            v.setCount(1);
            v.setInterestID(visit.getInterestID());
            v.setStationID(visit.getStationID());
            visitRepository.save(v);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
