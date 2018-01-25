package org.vaadin.example;

import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import org.vaadin.viritin.fields.MCheckBox;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public class FilterPanel extends CustomComponent implements ValueChangeListener {

    @FunctionalInterface
    public static interface FilterPanelObserver {

        void onFilterChange();
    }

    private MCheckBox onlyOnMap = new MCheckBox("Only events in current viewport").withValue(false)
            .withValueChangeListener(this);
    private MTextField title = new MTextField("Title")
            .withValueChangeMode(ValueChangeMode.LAZY)
            .withValueChangeListener(this);
    private FilterPanelObserver observer = null;

    public FilterPanel() {
        setCompositionRoot(new MHorizontalLayout(onlyOnMap, title));
    }

    public boolean isOnlyOnMap() {
        return onlyOnMap.getValue();
    }

    public String getTitle() {
        return title.getValue();
    }

    public void setObserver(FilterPanelObserver observer) {
        this.observer = observer;
    }

    @Override
    public void valueChange(HasValue.ValueChangeEvent event) {
        observer.onFilterChange();
    }

}
