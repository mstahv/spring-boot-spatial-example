package org.vaadin.example;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import org.vaadin.addons.maplibre.LineStringField;
import org.vaadin.addons.maplibre.PointField;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

public class EventEditor extends AbstractForm<SportEvent> {

    private TextField title = new VTextField("Title");
    private DatePicker date = new DatePicker("Date");
    // TODO fix the constructor parameter to be "field like"
    private PointField location = new PointField("Location");
    private LineStringField route = new LineStringField("Route");

    public EventEditor() {
        super(SportEvent.class);
    }

    @Override
    protected Component createContent() {
        location.setHeight("200px");
        location.setWidth("100%");
        route.setHeight("200px");
        route.setWidth("100%");
        return new VVerticalLayout(title, date, location, route, getToolbar());
    }

    @Override
    public VDialog openInModalPopup() {
        var w = super.openInModalPopup();
        w.setHeight("95%");
        w.setWidth("70%");
        return w;
    }

    @Override
    protected void bind() {
        getBinder().forMemberField(date).withConverter(new LocalDateToDateConverter());
        super.bind();
    }

}
