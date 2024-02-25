package org.vaadin.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Entity
public class SportEvent extends AbstractEntity {

    @NotEmpty
    private String title;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @NotNull
    @Column(columnDefinition = "geometry")
    private Point location;

    @Column(columnDefinition = "geometry")
    private LineString route;

    public SportEvent() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LineString getRoute() {
        return route;
    }

    public void setRoute(LineString route) {
        this.route = route;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

}
