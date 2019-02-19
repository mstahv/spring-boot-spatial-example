package org.vaadin.example;

import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author mstahv
 */
public interface SpatialEventRepository extends JpaRepository<SpatialEvent, Long> {

    /**
     * Example method of a GIS query. This uses Hibernate spatial extensions, so
     * it does not work with other JPA implementations.
     *
     * @param bounds the geometry
     * @param titleFilter the filter string
     * @return SpatialEvents inside given geometry and with given filter for the title
     */
    @Query(value = "SELECT se FROM SpatialEvent se WHERE within(se.location, :bounds) = true AND se.title LIKE :filter")
    public List<SpatialEvent> findAllWithin(@Param("bounds") Geometry bounds, @Param("filter") String titleFilter);

}
