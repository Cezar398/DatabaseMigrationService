package com.assist.internship.migrationservice.api.v1.movie.specification;

import com.assist.internship.migrationservice.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class MovieSpecification implements Specification<Movie> {

    private final MovieSearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Movie> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {

        List<Predicate> predicateList = new ArrayList<>();

        if(criteria.getVoteCount() != null) {
           predicateList.add(builder.greaterThanOrEqualTo(root.get("voteCount"), criteria.getVoteCount()));
        }

        if(criteria.getVoteAverage() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(root.get("voteAverage"), criteria.getVoteAverage()));
        }

        if(StringUtils.hasText(criteria.getTitle())) {
            predicateList.add(builder.like(root.get("title"), "%" + criteria.getTitle() + "%"));
        }

        if(criteria.getOverview() != null) {
            predicateList.add(builder.like(root.get("overview"), "%" + criteria.getOverview() + "%"));
        }
        return builder.and(predicateList.toArray(new Predicate[0]));
    }


}
