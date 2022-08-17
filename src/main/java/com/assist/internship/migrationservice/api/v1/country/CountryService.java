package com.assist.internship.migrationservice.api.v1.country;

import com.assist.internship.migrationservice.api.v1.country.dto.CountryDto;
import com.assist.internship.migrationservice.api.v1.country.dto.CreateCountryDto;
import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.entity.Country;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final MovieService movieService;
    //TODO: remove unused variable
    private final EntityManager entityManager;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country createCountry(CountryDto countryDto) {
        Country country = mapToCountry(countryDto);

        countryRepository.save(country);

        return country;
    }

    public Country findById(String id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found!"));
    }

    public Country addMovieToCountry(CreateCountryDto countryDto) {
        Movie movie = movieService.findById(countryDto.getMovieId());
        Country country = findById(countryDto.getCountryId());
        movie.getCountries().add(country);
        country.getMovieList().add(movie);

        movieService.save(movie);
        countryRepository.save(country);
        return country;
    }

    public void deleteAll() {
        countryRepository.deleteAll();
    }

    public void deleteById(String id) {
        countryRepository.deleteById(id);
    }

    private Country mapToCountry(CountryDto countryDto) {
        Country country = new Country();
        country.setName(countryDto.getName());
        return country;
    }


}
