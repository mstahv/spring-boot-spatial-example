
package org.vaadin.example;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author mstahv
 */
public interface SpatialEventRepository
		extends JpaRepository<SpatialEvent, Long>, QueryDslPredicateExecutor<SpatialEvent> {
	public List<SpatialEvent> findByTitleIgnoreCaseContaining(String title);
}
