package org.vaadin.example;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.vaadin.addons.maplibre.LinePaint;
import org.vaadin.addons.maplibre.MapLibre;
import org.vaadin.example.FilterPanel.FilterPanelObserver;
import org.vaadin.firitin.components.RichText;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.grid.VGrid;

/**
 * @author mstahv
 */
@Route
public class MainView extends VerticalLayout implements FilterPanelObserver {

    SportEventRepository repo;

    public MainView(SportEventRepository repo, SportEventService service) {
        this.repo = repo;
        if(repo.count() == 0) {
            service.insertTestData();
        }
        add(map);
        add(table);

        loadEvents(false, null);

        /*
        map.zoomToExtent(new Bounds(new Point(60, 21), new Point(68, 24)));

        filterPanel.setObserver(this);
        table = new MGrid<>(SpatialEvent.class);
        table.withProperties("id", "title", "date");
        table.addComponentColumn(spatialEvent -> {
            return new MHorizontalLayout(
                    new MButton(VaadinIcons.EDIT, e -> {
                        editInPopup(spatialEvent);
                    }).withStyleName(ValoTheme.BUTTON_BORDERLESS),
                    new MButton(VaadinIcons.TRASH, e -> {
                        repo.delete(spatialEvent);
                        loadEvents(filterPanel.isOnlyOnMap(), filterPanel.getTitle());
                    }).withStyleName(ValoTheme.BUTTON_BORDERLESS));
        }).setCaption("Actions");

        loadEvents(filterPanel.isOnlyOnMap(), filterPanel.getTitle());

        osmTiles.setAttributionString("© OpenStreetMap Contributors");
        final MVerticalLayout mainLayout = new MVerticalLayout(
                infoText,
                new MHorizontalLayout().expand(filterPanel).add(addNew))
                .alignAll(Alignment.MIDDLE_LEFT);
        if (Page.getCurrent().getBrowserWindowWidth() > 800) {
            mainLayout.expand(new MHorizontalLayout().expand(map).add(table).withFullHeight());
        } else {
            // in moble devices, layout out vertically
            mainLayout.expand(map, table);
        }

        setContent(mainLayout);

        // You can also use ContextMenu Add-on with Leaflemap
        // Give "false" as a second parameter -> disables automatic opening of the menu.
        // We'll open context menu programmatically
        ContextMenu contextMenu = new ContextMenu(map, false);
        contextMenu.addItem("Add new event here", e -> {
            SpatialEvent eventWithPoint = new SpatialEvent();
            eventWithPoint.setLocation(JTSUtil.toPoint(lastContextMenuPosition));
            editInPopup(eventWithPoint);
        });

        // Hook to context menu event API by the Leaflet Map
        map.addContextMenuListener(event -> {
            // save the point to be used by context menu listener
            lastContextMenuPosition = event.getPoint();
            // you could here also configure what to show in the menu
            contextMenu.open((int) event.getClientX(), (int) event.getClientY());
        });

        map.addMoveEndListener(event -> {
            if (filterPanel.isOnlyOnMap()) {
                onFilterChange();
            }
        });

        editor.setSavedHandler(spatialEvent -> {
            repo.save(spatialEvent);
            editor.closePopup();
            loadEvents(filterPanel.isOnlyOnMap(), filterPanel.getTitle());
        });
        */

    }

    private Point lastContextMenuPosition;

    private RichText infoText = new RichText().withMarkDown(
            "###V-Leaflet example\n\n"
            + "This is small example app to demonstrate how to add simple GIS "
            + "features to your Spring Boot Vaadin app. "
            + "[Check out the sources!](https://github.com/mstahv/spring-boot-spatial-example)");
    private VGrid<SportEvent> table = new VGrid<>(SportEvent.class);
    private Button addNew = new VButton("Add event", this).withIcon(VaadinIcon.PLUS.create());
    // TODO get a new API key
    private MapLibre map = new MapLibre("https://api.maptiler.com/maps/streets/style.json?key=G5n7stvZjomhyaVYP0qU");

    private EventEditor editor = new EventEditor();

    private FilterPanel filterPanel = new FilterPanel();


    private void editInPopup(SportEvent eventWithPoint) {
        editor.setEntity(eventWithPoint);
        editor.openInModalPopup();
    }

    private void loadEvents(boolean onlyInViewport, String titleContains) {

        List<SportEvent> events;
        /*
        TODO bounds changed listener to MapLibre
        if (map.getBounds() != null) {
            Polygon polygon = toPolygon(map.getBounds());
            events = repo.findAllWithin(polygon, "%" + titleContains + "%");
        } else {
            events = repo.findAll();
        }
         */
        events = repo.findAll();

        /* Populate table... */
        table.setItems(events);

        /* ... and map */
        for (final SportEvent sportEvent : events) {
            addToMap(sportEvent.getLocation(), sportEvent);
            addToMap(sportEvent.getRoute(), sportEvent);
        }
        if (!filterPanel.isOnlyOnMap()) {
            // TODO add feature to MapLibre
            // map.zoomToContent();
        }
    }

    private void addToMap(final Geometry g, final SportEvent event) {
        if (g != null) {
            // TODO pick to editor from click listeners
            if(g instanceof Point p) {
                map.addMarker(p.getX(), p.getY());
            } else if(g instanceof LineString ls) {
                map.addLineLayer(ls, new LinePaint("blue", 3.0));
            }
        }
    }

    /*
    private Polygon toPolygon(Bounds bounds) {
        GeometryFactory factory = new GeometryFactory();
        double north = bounds.getNorthEastLat();
        double south = bounds.getSouthWestLat();
        double west = bounds.getSouthWestLon();
        double east = bounds.getNorthEastLon();
        Coordinate[] coords = new Coordinate[]{new Coordinate(east, north), new Coordinate(east, south),
            new Coordinate(west, south), new Coordinate(west, north), new Coordinate(east, north)};
        // GeoDb does not support LinerRing intersection, but polygon ?!
        LinearRing lr = factory.createLinearRing(coords);
        Polygon polygon = factory.createPolygon(lr, null);
        polygon.setSRID(4326);
        return polygon;
    }
     */

//
//    @Override
//    public void buttonClick(Button.ClickEvent event) {
//        if (event.getButton() == addNew) {
//            editor.setEntity(new SpatialEvent());
//            editor.focusFirst();
//            editor.openInModalPopup();
//        }
//    }
//
//    @Override
//    public void addWindow(Window window) throws IllegalArgumentException,
//            NullPointerException {
//        super.addWindow(window);
//        window.addCloseListener(this);
//    }
//
//    @Override
//    public void windowClose(Window.CloseEvent e) {
//        // refresh table after edit
//        loadEvents(filterPanel.isOnlyOnMap(), filterPanel.getTitle());
//    }
//
    @Override
    public void onFilterChange() {
        loadEvents(filterPanel.isOnlyOnMap(), filterPanel.getTitle());
    }

}
