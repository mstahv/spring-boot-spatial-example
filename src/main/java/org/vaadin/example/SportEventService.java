package org.vaadin.example;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SportEventService {

    private final SportEventRepository repo;

    public SportEventService(SportEventRepository repository) {
        this.repo = repository;
    }

    public void insertTestData() {
        GeometryFactory factory = new GeometryFactory();

        SportEvent theEvent = new SportEvent();
        theEvent.setTitle("Example event");
        theEvent.setDate(new Date());
        theEvent.setLocation(factory.createPoint(new Coordinate(26, 62)));
        theEvent.getLocation().setSRID(4326);
        repo.save(theEvent);

        SportEvent eventWithPath = new SportEvent();
        Coordinate[] coords = new Coordinate[]{new Coordinate(22, 60),
                new Coordinate(23, 61), new Coordinate(22, 63)};
        eventWithPath.setRoute(factory.createLineString(coords));
        eventWithPath.getRoute().setSRID(4326);
        eventWithPath.setLocation(factory.createPoint(new Coordinate(22, 60)));
        eventWithPath.getLocation().setSRID(4326);
        eventWithPath.setDate(new Date());
        eventWithPath.setTitle("MTB cup 1/10");
        repo.save(eventWithPath);

    }
}
