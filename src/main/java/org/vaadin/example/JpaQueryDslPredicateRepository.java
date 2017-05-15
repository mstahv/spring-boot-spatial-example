package org.vaadin.example;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * Convenience interface for {@link QueryDslPredicateExecutor} with
 * {@link JpaRepository}. Returns {@link List} instead of {@link Iterable}.
 * 
 * @see {@link QueryDslJpaRepository}
 * @see <a href=
 *      "https://jira.spring.io/browse/DATACMNS-126">https://jira.spring.io/browse/DATACMNS-126</a>
 */
@NoRepositoryBean
public interface JpaQueryDslPredicateRepository<T, ID extends Serializable>
		extends QueryDslPredicateExecutor<T>, JpaRepository<T, ID> {
	@Override
	List<T> findAll(OrderSpecifier<?>... orders);

	@Override
	List<T> findAll(Predicate predicate);

	@Override
	List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders);

	@Override
	List<T> findAll(Predicate predicate, Sort sort);
}
