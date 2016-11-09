
package org.vaadin.example;

import com.vaadin.addon.contextmenu.ContextMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import java.util.List;
import org.vaadin.addon.leaflet.AbstractLeafletLayer;
import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.LOpenStreetMapLayer;
import org.vaadin.addon.leaflet.LTileLayer;
import org.vaadin.addon.leaflet.shared.Point;
import org.vaadin.addon.leaflet.util.JTSUtil;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author mstahv
 */
@Theme("valo")
@SpringUI
public class VaadinUI extends UI implements ClickListener, Window.CloseListener {
    
    SpatialEventRepository repo;

    public VaadinUI(SpatialEventRepository repo) {
        this.repo = repo;
    }

    
    private Point lastContextMenuPosition;


    private RichText infoText = new RichText().withMarkDown(
            "###V-Leaflet example\n\n"
            + "This is small example app to demonstrate how to add simple GIS "
            + "features to your Vaadin apps. "
            + "[Check out the sources!](https://github.com/mstahv/vleafletexample)");
    private MTable<SpatialEvent> table;
    private Button addNew = new Button("Add event", this);
    private LMap map = new LMap();
    private LTileLayer osmTiles = new LOpenStreetMapLayer();
    
    private EventEditor editor = new EventEditor();

    @Override
    protected void init(VaadinRequest request) {

        table = new MTable<>(SpatialEvent.class);
        table.setWidth("100%");

        table.withGeneratedColumn("Actions", spatialEvent -> {
            Button edit = new MButton(FontAwesome.EDIT, e -> {
                editInPopup(spatialEvent);
            });
            Button delete = new MButton(FontAwesome.TRASH, e -> {
                repo.delete(spatialEvent);
                loadEvents();
            });
            return new MHorizontalLayout(edit, delete);
        });
        table.withProperties("id", "title", "date", "Actions");

        loadEvents();

        osmTiles.setAttributionString("© OpenStreetMap Contributors");

        setContent(new MVerticalLayout(infoText, new MHorizontalLayout(addNew)).expand(map, table));

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
        
        editor.setSavedHandler(spatialEvent -> {
            repo.save(spatialEvent);
            editor.closePopup();
            loadEvents();
        });

    }

    private void editInPopup(SpatialEvent eventWithPoint) {
        editor.setEntity(eventWithPoint);
        editor.openInModalPopup();
    }

    private void loadEvents() {

        List<SpatialEvent> events = repo.findAll();

        /* Populate table... */
        table.setRows(events);

        /* ... and map */
        map.removeAllComponents();
        map.addBaseLayer(osmTiles, "OSM");
        for (final SpatialEvent spatialEvent : events) {
            addEventVector(spatialEvent.getLocation(), spatialEvent);
            addEventVector(spatialEvent.getRoute(), spatialEvent);
        }
        map.zoomToContent();
    }

    private void addEventVector(final com.vividsolutions.jts.geom.Geometry g, final SpatialEvent spatialEvent) {
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
        loadEvents();
    }

}
