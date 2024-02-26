package org.vaadin.example;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.addons.maplibre.LineStringField;
import org.vaadin.addons.maplibre.PointField;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

@Route
public class EventEditor extends AbstractForm<SportEvent> {

    private final SportEventService service;
    private TextField title = new VTextField("Title");
    private DatePicker date = new DatePicker("Date");
    private PointField location = new PointField("Location");
    private LineStringField route = new LineStringField("Route");

    public EventEditor(SportEventService service) {
        super(SportEvent.class);
        this.service = service;
        setSavedHandler(sportevent -> {
            service.save(sportevent);
            UI.getCurrent().navigate(MainView.class);
        });

        setResetHandler(sportevent -> {
            UI.getCurrent().navigate(MainView.class);
        });
        getDeleteButton().setVisible(false);
    }

    @Override
    protected Component createContent() {
        getContent().setSizeFull();
        location.setSizeFull();
        route.setSizeFull();
        return new VVerticalLayout()
                .withComponent(new VHorizontalLayout(title, date))
                .withExpanded(new VHorizontalLayout(location, route)
                        .withSizeFull())
                .withComponent(getToolbar())
                .withFullHeight();
    }


}
