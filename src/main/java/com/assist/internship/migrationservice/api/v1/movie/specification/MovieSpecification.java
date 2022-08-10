package com.assist.internship.migrationservice.api.v1.movie.specification;

import com.assist.internship.migrationservice.entity.Movie;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class MovieSpecification implements Specification<Movie> {

    private final MovieSearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Movie> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        if (StringUtils.hasText(criteria.getTitle())) {
            return builder.like(root.get("title"), criteria.getTitle());
        }
        //TODO: add more criteria
        return null;
    }
}
