package org.vaadin.example;

import java.util.Date;

import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

@Entity
public class SpatialEvent extends AbstractEntity {

    private String title;

    @Temporal(TemporalType.DATE)
    private Date date;

    //@Column(columnDefinition = "POINT") // this type is known by MySQL
    @Column(columnDefinition = "geometry")
    private Point location;

    // @Column(columnDefinition = "POLYGON") // this type is known by MySQL
    @Column(columnDefinition = "geometry")
    private LineString route;

    public SpatialEvent() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
