package org.vaadin.example;

import org.vaadin.viritin.fields.MCheckBox;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.CustomComponent;


@SuppressWarnings("serial")
public class FilterPanel extends CustomComponent implements ValueChangeListener {
	@FunctionalInterface
	public static interface FilterPanelObserver {
		void onFilterChange();
	}

	private MCheckBox onlyOnMap = new MCheckBox("Only events in current viewport").withValue(false)
			.withValueChangeListener(this);
	private MTextField title = new MTextField("Title").addTextChangeListener(this);
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
		if (event.getSource() == title) {
			textChange(event);
		} else {
		if (observer != null) {
			observer.onFilterChange();
			}
		}
	}

	public void textChange(ValueChangeEvent<String> event) {
		titleText = event.getValue();
		if (observer != null) {
			observer.onFilterChange();
		}
	}
}
