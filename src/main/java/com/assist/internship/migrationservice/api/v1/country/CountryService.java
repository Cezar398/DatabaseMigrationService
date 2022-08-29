package com.assist.internship.migrationservice.api.v1.country;

import com.assist.internship.migrationservice.api.v1.country.dto.AddMovieDto;
import com.assist.internship.migrationservice.api.v1.country.dto.CountryDto;
import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.entity.Country;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final MovieService movieService;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country createCountry(CountryDto countryDto) {
        Country country = mapToCountry(countryDto);

        countryRepository.save(country);

        return country;
    }

    public Country findById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found!"));
    }

    public Country addMovieToCountry(AddMovieDto countryDto) {
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

    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }

    private Country mapToCountry(CountryDto countryDto) {
        Country country = new Country();
        country.setName(countryDto.getName());
        return country;
    }


}
