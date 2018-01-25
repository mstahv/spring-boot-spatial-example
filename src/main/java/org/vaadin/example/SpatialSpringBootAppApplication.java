package org.vaadin.example;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpatialSpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpatialSpringBootAppApplication.class, args);
	}
        
        @Bean
        CommandLineRunner init(SpatialEventRepository repo) {
            return (String... strings) -> {
		GeometryFactory factory = new GeometryFactory();
                
                SpatialEvent theEvent = new SpatialEvent();
		theEvent.setTitle("Example event");
		theEvent.setDate(new Date());
		theEvent.setLocation(factory.createPoint(new Coordinate(26, 62)));
                theEvent.getLocation().setSRID(4326);
		repo.save(theEvent);

		SpatialEvent eventWithPath = new SpatialEvent();
		Coordinate[] coords = new Coordinate[]{new Coordinate(22, 60),
			new Coordinate(23, 61), new Coordinate(22, 63)};
		eventWithPath.setRoute(factory.createLineString(coords));
                eventWithPath.getRoute().setSRID(4326);
		eventWithPath.setLocation(factory.createPoint(new Coordinate(22, 60)));
                eventWithPath.getLocation().setSRID(4326);
		eventWithPath.setDate(new Date());
		eventWithPath.setTitle("MTB cup 1/10");
		repo.save(eventWithPath);
                
            };
        }
}
