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
            for(int i = 0; i < 10; i++) {
                Visit v = new Visit();
                v.setStationID(i+1);
                v.setTotal(10);
                v.setDate(LocalDate.now());
                Map<Integer, Integer> counts = new HashMap<>();
                counts.put(rnd.nextInt(8), rnd.nextInt(30));
                counts.put(rnd.nextInt(8), rnd.nextInt(30));
                visitRepository.save(v);
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

    @GetMapping("/visits/{date}")
    public List<Visit> getVisitsForDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return visitRepository.findByDate(localDate);
    }

    @GetMapping("/visits/date/{date}/station/{stationID}")
    public Visit getVisitForDateAndStation(@PathVariable String date, @PathVariable int stationID) {
        LocalDate localDate = LocalDate.parse(date);
        return visitRepository.findVisitByDateAndStationID(localDate, stationID);
    }

    @GetMapping("/visits/totalPerStation")
    public List<Visit> getTotalPerStation() {
        List<Visit> visits = visitRepository.findAll();
        Map<Integer, List<Visit>> visitsGrouped =
                visits.stream().collect(Collectors.groupingBy(w -> w.getStationID()));

        List<Visit> visitsPerStation = new ArrayList<>();

        for (Map.Entry<Integer, List<Visit>> visitsOfStation : visitsGrouped.entrySet()) {
            Visit visit = new Visit();
            visit.setStationID(visitsOfStation.getKey());
            int sum = visitsOfStation.getValue().stream().mapToInt(o -> o.getTotal()).sum();
            visit.setTotal(sum);

            visitsPerStation.add(visit);
        }

        return visitsPerStation;
    }

    @PutMapping("/visit")
    public ResponseEntity<Void> createOrUpdate(@RequestBody VisitDTO visit) {
        Optional<Visit> visitOptional = Optional.ofNullable(visitRepository.findVisitByDateAndStationID(LocalDate.now(), visit.getStationID()));
        if (visitOptional.isPresent()) {
            int interestID = visit.getInterestID();
            Visit v = visitOptional.get();
            if (v.getCountPerInterests().get(interestID) != null) {
                v.getCountPerInterests().merge(interestID, 1, Integer::sum);
            } else {
                v.getCountPerInterests().put(interestID, 1);
            }
            v.incrementTotal();
            visitRepository.save(v);
        } else {
            Map<Integer, Integer> counts = new HashMap<>();
            counts.put(visit.getInterestID(), 1);
            Visit v = new Visit();
            v.setTotal(1);
            v.setDate(LocalDate.now());
            v.setCountPerInterests(counts);
            v.setStationID(visit.getStationID());
            visitRepository.save(v);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
