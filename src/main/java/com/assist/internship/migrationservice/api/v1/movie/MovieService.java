package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.exception.CsvApiException;
import com.assist.internship.migrationservice.api.v1.movie.dto.CSVDto;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSearchCriteria;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSpecification;
import com.assist.internship.migrationservice.config.properties.ExportConfig;
import com.assist.internship.migrationservice.config.properties.ImportConfig;
import com.assist.internship.migrationservice.entity.Movie;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ExportConfig exportConfig;
    private final ImportConfig importConifg;
    @Autowired
    private HttpServletRequest httpServletRequest;

    public Movie create(MovieDto movieDto) {
        Movie movie = mapToMovie(movieDto);
        movieRepository.save(movie);

        return movie;
    }

    public List<Movie> getAll(MovieSearchCriteria movieSearchCriteria) {
        MovieSpecification specification = new MovieSpecification(movieSearchCriteria);
        return movieRepository.findAll(specification);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found!"));
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public void deleteById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found!"));

        movieRepository.delete(movie);
    }

    public Movie updateById(Long id, MovieDto movieDto) {
        Movie oldMovie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        Movie updatedMovie = mapToMovie(movieDto);
        updatedMovie.setId(oldMovie.getId());

        return movieRepository.save(updatedMovie);
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public void saveMovies(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    public void exportToCSV(HttpServletResponse response) {
        try {
            response.setContentType(exportConfig.getContentType());
            response.setHeader(exportConfig.getHeaderKey(), exportConfig.getHeaderValue() + Movie.class.getSimpleName() + exportConfig.getFileFormat());
            PrintWriter writer = new PrintWriter(response.getWriter());
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(mapToDto(findAll()));
            writer.close();
        } catch (Exception e) {
            throw new CsvApiException(e.getMessage());
        }
    }

    public List<Movie> findLast10() {
        return movieRepository.findLast10();
    }

    public void importFromCSV(MultipartFile multipartFile) {
        try {
            Reader reader = new InputStreamReader(multipartFile.getInputStream());
            List<CSVDto> csvDtoList = new CsvToBeanBuilder(reader)
                    .withType(CSVDto.class)
                    .build()
                    .parse();
            saveMovies(mapToMovies(csvDtoList));
        } catch (Exception e) {
            log.error("Error CSV: {}", e.getMessage());
            throw new CsvApiException(e.getMessage());
        }
    }

    private List<CSVDto> mapToDto(List<Movie> movies) {
        return movies.stream()
                .map(this::movieToDto)
                .collect(Collectors.toList());
    }

    private List<Movie> mapToMovies(List<CSVDto> csvDtos) {
        return csvDtos.stream()
                .map(this::csvBeanToMovie)
                .collect(Collectors.toList());
    }

    private Movie csvBeanToMovie(CSVDto csvDto) {
        Movie movie = new Movie();
        movie.setId(csvDto.getId());
        movie.setExternalId(csvDto.getExternalId());
        movie.setTitle(csvDto.getTitle());
        movie.setOverview(csvDto.getOverview());
        movie.setPosterPath(csvDto.getPosterPath());
        movie.setMediaType(csvDto.getMediaType());
        movie.setPopularity(csvDto.getPopularity());
        movie.setReleaseDate(csvDto.getReleaseDate());
        movie.setVideo(csvDto.getVideo());
        movie.setVoteAverage(csvDto.getVoteAverage());
        movie.setVoteCount(csvDto.getVoteCount());
        return movie;
    }


    private CSVDto movieToDto(Movie movie) {
        CSVDto csvDto = new CSVDto();
        csvDto.setId(movie.getId());
        csvDto.setExternalId(movie.getExternalId());
        csvDto.setTitle(movie.getTitle());
        csvDto.setOverview(movie.getOverview());
        csvDto.setPosterPath(movie.getPosterPath());
        csvDto.setMediaType(movie.getMediaType());
        csvDto.setPopularity(movie.getPopularity());
        csvDto.setReleaseDate(movie.getReleaseDate());
        csvDto.setVideo(movie.getVideo());
        csvDto.setVoteAverage(movie.getVoteAverage());
        csvDto.setVoteCount(movie.getVoteCount());
        return csvDto;
    }

    private Movie mapToMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setMediaType(movieDto.getMediaType());
        movie.setOverview(movieDto.getOverview());
        movie.setPopularity(movieDto.getPopularity());
        movie.setPosterPath(movieDto.getPosterPath());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setVideo(movieDto.getVideo());
        movie.setVoteAverage(movieDto.getVoteAverage());
        movie.setVoteCount(movieDto.getVoteCount());
        movie.setTitle(movieDto.getTitle());
        return movie;
    }
}
