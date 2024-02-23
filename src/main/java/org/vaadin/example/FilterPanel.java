package org.vaadin.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class FilterPanel extends VerticalLayout {



    @FunctionalInterface
    public static interface FilterPanelObserver {

        void onFilterChange();
    }

//    private MCheckBox onlyOnMap = new MCheckBox("Only events in current viewport").withValue(true)
//            .withValueChangeListener(this);
//    private MTextField title = new MTextField()
//            .withPlaceholder("Filter by title")
//            .withValueChangeMode(ValueChangeMode.LAZY)
//            .withValueChangeListener(this);
//    private FilterPanelObserver observer = null;

    public FilterPanel() {
        add("TODO");
        //setCompositionRoot(new MHorizontalLayout(onlyOnMap, title));
    }

    public boolean isOnlyOnMap() {
        return true;
//        return onlyOnMap.getValue();
    }

    public String getTitle() {
        return "";
  //      return title.getValue();
    }
//
//    public void setObserver(FilterPanelObserver observer) {
//        this.observer = observer;
//    }
//
//    @Override
//    public void valueChange(HasValue.ValueChangeEvent event) {
//        observer.onFilterChange();
//    }

}
