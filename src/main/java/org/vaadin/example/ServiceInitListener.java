package org.vaadin.example;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;
import org.vaadin.addons.maplibre.MapLibreBaseMapProvider;

@Component
public class ServiceInitListener implements VaadinServiceInitListener {
  @Override
  public void serviceInit(ServiceInitEvent serviceEvent) {
    // Configure the default backround layer, used by MapLibre components
    // if nothing else is defined
    // TODO create new key for this demo, this is technically for the add-on demo/test
    serviceEvent.getSource().getContext().setAttribute(MapLibreBaseMapProvider.class, () -> {
      return "https://api.maptiler.com/maps/streets/style.json?key=G5n7stvZjomhyaVYP0qU";
    });
  }
}