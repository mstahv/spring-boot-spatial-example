package org.vaadin.example;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SportEventService {

    private final SportEventRepository repo;

    public SportEventService(SportEventRepository repository) {
        this.repo = repository;
    }

    public void ensureTestData() {
        if (repo.count() == 0) {

            GeometryFactory factory = new GeometryFactory();

            WKTReader wktReader = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326));

            try {
                SportEvent theEvent = new SportEvent();
                theEvent.setTitle("Example event");
                theEvent.setDate(LocalDate.now());
                theEvent.setLocation((Point) wktReader.read("POINT (26 62)"));
                theEvent.setRoute((LineString) wktReader.read("LINESTRING (26 62, 27 62, 27 63)"));
                repo.save(theEvent);

                SportEvent eventWithPath = new SportEvent();
                eventWithPath.setRoute((LineString) wktReader.read("LINESTRING (22 60, 23 61, 22 63)"));
                eventWithPath.setLocation((Point) wktReader.read("POINT (22 60)"));
                eventWithPath.setDate(LocalDate.now());
                eventWithPath.setTitle("MTB cup 1/10");
                repo.save(eventWithPath);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void save(SportEvent sportevent) {
        repo.save(sportevent);
    }

    public void delete(SportEvent event) {
        repo.deleteById(event.getId());
    }

    public List<SportEvent> filterByTitle(String value) {
        return repo.findByTitleContainingIgnoreCase(value);
    }

    public List<SportEvent> filterByBounds(Polygon bounds) {
        return repo.findAllWithin(bounds);
    }
}
