package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.vaadin.addons.maplibre.DrawControl;
import org.vaadin.addons.maplibre.LinePaint;
import org.vaadin.addons.maplibre.MapLibre;
import org.vaadin.addons.maplibre.Marker;
import org.vaadin.firitin.components.RichText;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mstahv
 */
@Route
public class MainView extends VVerticalLayout {

    private final SportEventService service;
    TextField filter = new VTextField()
            .withPlaceholder("Filter by name...");

    private Map<SportEvent,Marker> eventToMarker = new HashMap<>();

    private RichText infoText = new RichText().withMarkDown(
            "###V-Leaflet example\n\n"
                    + "This is small example app to demonstrate how to add simple GIS "
                    + "features to your Spring Boot Vaadin app. "
                    + "[Check out the sources!](https://github.com/mstahv/spring-boot-spatial-example)");
    private VGrid<SportEvent> table = new VGrid<>(SportEvent.class);
    private Button addNew = new VButton("New event...")
            .withIcon(VaadinIcon.PLUS.create())
            .withClickListener(e -> {
                UI.getCurrent().navigate(EventEditor.class)
                        .get().setEntity(new SportEvent());
            });

    // Note, the base map here and in editors could be defined
    // here, but are instead defined application wide in Application class
    private MapLibre map = new MapLibre();

    public MainView(SportEventService service) {
        this.service = service;
        service.ensureTestData();

        var drawControl = new DrawControl(map);
        drawControl.addGeometryChangeListener(e -> {
            Polygon p = (Polygon) e.getGeom().getGeometryN(0);
            loadEventsWithinBounds(p);
            drawControl.clear();
        });

        add(new HorizontalLayout(
                addNew,
                new VButton("Draw area to list events", e -> {
                    drawControl.setMode(DrawControl.DrawMode.DRAW_POLYGON);
                }),
                new VButton("List events within viewport", e -> {
                    loadEventsInViewport();
                }),
                filter
        ));
        withExpanded(map, table);

        filter.addValueChangeListener(e -> {
            loadEventsByNameFilter(e.getValue());
        });

        table.addComponentColumn(sportEvent ->
                new HorizontalLayout(
                        new VButton(VaadinIcon.EDIT.create(), e-> {
                            UI.getCurrent().navigate(EventEditor.class).get()
                                    .setEntity(sportEvent);
                        }),
                        new DeleteButton(() -> {
                            delete(sportEvent);
                        })
                ));



        table.asSingleSelect().addValueChangeListener(e -> {
            SportEvent sportEvent = e.getValue();
            if(e.isFromClient() && sportEvent != null) {
                // open marker popup and center the map to event
                Marker marker = eventToMarker.get(sportEvent);
                marker.openPopup();
                map.flyTo(marker.getGeometry(), 10);
            }
        });

        loadEventsByNameFilter("");
        map.fitToContent();

    }

    public void delete(SportEvent event) {
        service.delete(event);
        loadEventsInViewport();
    }

    private void loadEventsByNameFilter(String value) {
        List<SportEvent> events = service.filterByTitle(value);
        map.fitToContent();
        setEvents(events);
    }

    private void loadEventsInViewport() {
        map.getViewPort().thenAccept(vp -> {
            Polygon bounds = vp.getBounds();
            loadEventsWithinBounds(bounds);
        });
    }

    private void loadEventsWithinBounds(Polygon bounds) {
        setEvents(service.filterByBounds(bounds));
    }

    private void setEvents(List<SportEvent> events) {
        /* Populate table... */
        table.setItems(events);
        /* ... and map */
        map.removeAll();
        eventToMarker.clear();
        for (final SportEvent sportEvent : events) {
            /*
             * Adds geometries to the map. Note that this method adds a separate
             * layer per geometry and is thus not very optimised. For better
             * performance with a large number of geometries, combine layers
             * or load features as vector tiles (~ lazy load only the visible portion)
             * if there is a ton of those.
             */

            if(sportEvent.getLocation() != null){
                Point p = sportEvent.getLocation();
                Marker marker = map.addMarker(p)
                        .withPopup(sportEvent.getTitle());
                eventToMarker.put(sportEvent,marker);
                marker.addClickListener( () -> {
                    // focus in Table
                    table.asSingleSelect().setValue(sportEvent);
                });
            }
            if(sportEvent.getRoute() != null) {
                map.addLineLayer(sportEvent.getRoute(), new LinePaint("blue", 3.0));
                // TODO add click listener also for lines
            }
        }
    }

}
