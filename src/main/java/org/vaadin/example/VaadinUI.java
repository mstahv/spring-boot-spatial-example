package org.vaadin.example;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.addon.leaflet.AbstractLeafletLayer;
import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.LOpenStreetMapLayer;
import org.vaadin.addon.leaflet.LTileLayer;
import org.vaadin.addon.leaflet.shared.Bounds;
import org.vaadin.addon.leaflet.shared.Point;
import org.vaadin.addon.leaflet.util.JTSUtil;
import org.vaadin.example.FilterPanel.FilterPanelObserver;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.contextmenu.ContextMenu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WebBrowser;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.vaadin.viritin.grid.MGrid;

/**
 * @author mstahv
 */
@Theme("valo")
@SpringUI
public class VaadinUI extends UI implements ClickListener, Window.CloseListener, FilterPanelObserver {

    SpatialEventRepository repo;

    public VaadinUI(SpatialEventRepository repo) {
        this.repo = repo;
    }

    private Point lastContextMenuPosition;

    private RichText infoText = new RichText().withMarkDown(
            "###V-Leaflet example\n\n"
            + "This is small example app to demonstrate how to add simple GIS "
            + "features to your Spring Boot Vaadin app. "
            + "[Check out the sources!](https://github.com/mstahv/spring-boot-spatial-example)");
    private MGrid<SpatialEvent> table;
    private Button addNew = new MButton("Add event", this).withIcon(VaadinIcons.PLUS);
    private LMap map = new LMap();
    private LTileLayer osmTiles = new LOpenStreetMapLayer();

    private EventEditor editor = new EventEditor();

    private FilterPanel filterPanel = new FilterPanel();

    @Override
    protected void init(VaadinRequest request) {

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

    }

    private void editInPopup(SpatialEvent eventWithPoint) {
        editor.setEntity(eventWithPoint);
        editor.openInModalPopup();
    }

    private void loadEvents(boolean onlyInViewport, String titleContains) {

        List<SpatialEvent> events;
        if (map.getBounds() != null) {
            Polygon polygon = toPolygon(map.getBounds());
            events = repo.findAllWithin(polygon, "%" + titleContains + "%");
        } else {
            events = repo.findAll();
        }

        /* Populate table... */
        table.setRows(events);

        /* ... and map */
        map.removeAllComponents();
        map.addBaseLayer(osmTiles, "OSM");
        for (final SpatialEvent spatialEvent : events) {
            addEventVector(spatialEvent.getLocation(), spatialEvent);
            addEventVector(spatialEvent.getRoute(), spatialEvent);
        }
        if (!filterPanel.isOnlyOnMap()) {
            map.zoomToContent();
        }
    }

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

    private void addEventVector(final Geometry g, final SpatialEvent spatialEvent) {
        if (g != null) {
            /*
            * JTSUtil wil make LMarker for point event,
            * LPolyline for events with route
             */
            AbstractLeafletLayer layer = (AbstractLeafletLayer) JTSUtil.
                    toLayer(g);

            /* Add click listener to open event editor */
            layer.addClickListener(e -> {
                editor.setEntity(spatialEvent);
                editor.focusFirst();
                editor.openInModalPopup();
            });
            map.addLayer(layer);
        }
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton() == addNew) {
            editor.setEntity(new SpatialEvent());
            editor.focusFirst();
            editor.openInModalPopup();
        }
    }

    @Override
    public void addWindow(Window window) throws IllegalArgumentException,
            NullPointerException {
        super.addWindow(window);
        window.addCloseListener(this);
    }

    @Override
    public void windowClose(Window.CloseEvent e) {
        // refresh table after edit
        loadEvents(filterPanel.isOnlyOnMap(), filterPanel.getTitle());
    }

    @Override
    public void onFilterChange() {
        loadEvents(filterPanel.isOnlyOnMap(), filterPanel.getTitle());
    }

}
