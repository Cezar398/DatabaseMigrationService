package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    public List<Movie> getMoviesByVoteCountGreaterThan(int voteCount);
    public List<Movie> getMoviesByVoteCountIsLessThan(int voteCount);
    public List<Movie> getMoviesByVoteCountGreaterThanEqual(int voteCount);
    public List<Movie> getMoviesByVoteCountIsLessThanEqual(int voteCount);
    public List<Movie> getMoviesByOverviewContains(String overview);
    public List<Movie> getMoviesByOverviewIsNotContaining(String overview);

}
