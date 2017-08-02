package org.vaadin.example;

import org.vaadin.addon.leaflet.editable.LineStringField;
import org.vaadin.addon.leaflet.util.PointField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class EventEditor extends AbstractForm<SpatialEvent> {

	public EventEditor(Class<SpatialEvent> entityType) {
		super(entityType);
	}

	private TextField title = new MTextField("Title");
    private DateField date = new DateField("Date");
    private PointField location = new PointField("Location");
    private LineStringField route = new LineStringField("Route");

    @Override
    protected Component createContent() {
        location.setHeight("200px");
        location.setWidth("100%");
        route.setHeight("200px");
        route.setWidth("100%");
        return new MVerticalLayout(title, date, location, route, getToolbar());
    }

    @Override
    public Window openInModalPopup() {
        Window w = super.openInModalPopup();
        w.setHeight("95%");
        w.setWidth("70%");
        return w;
    }
    
    

}
