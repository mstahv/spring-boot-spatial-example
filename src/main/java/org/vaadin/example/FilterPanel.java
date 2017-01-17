package org.vaadin.example;

import org.vaadin.viritin.fields.MCheckBox;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public class FilterPanel extends CustomComponent implements ValueChangeListener, TextChangeListener {
	@FunctionalInterface
	public static interface FilterPanelObserver {
		void onFilterChange();
	}

	private MCheckBox onlyOnMap = new MCheckBox("Only events in current viewport").withValue(false)
			.withValueChangeListener(this);
	private MTextField title = new MTextField("Title").withTextChangeListener(this);
	// need to store the value extra, because else the last text change is not
	// visible on TextField.getValue
	private String titleText = "";
	private FilterPanelObserver observer = null;

	public FilterPanel() {
		setCompositionRoot(new MHorizontalLayout(onlyOnMap, title));
	}

	public boolean isOnlyOnMap() {
		return onlyOnMap.getValue();
	}

	public String getTitle() {
		return titleText;
	}

	public void setObserver(FilterPanelObserver observer) {
		this.observer = observer;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if (observer != null) {
			observer.onFilterChange();
		}
	}

	@Override
	public void textChange(TextChangeEvent event) {
		titleText = event.getText();
		if (observer != null) {
			observer.onFilterChange();
		}
	}
}
