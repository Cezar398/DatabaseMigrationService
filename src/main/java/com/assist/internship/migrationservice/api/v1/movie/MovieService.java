package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.ExportDto;
import com.assist.internship.migrationservice.api.v1.movie.dto.ImportDto;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSearchCriteria;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSpecification;
import com.assist.internship.migrationservice.config.properties.ExportConfig;
import com.assist.internship.migrationservice.config.properties.ImportConfig;
import com.assist.internship.migrationservice.entity.Movie;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public void exportToCSV(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        response.setContentType(exportConfig.getContentType());
        response.setHeader(exportConfig.getHeaderKey(), exportConfig.getHeaderValue() + Movie.class.getSimpleName() + exportConfig.getFileFormat());
        List<Movie> movies = findAll();
        List<ExportDto> exportDtos  =  mapToExportDto(movies);
        PrintWriter writer = new PrintWriter(response.getWriter());
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(exportDtos);
        writer.close();
    }

    public List<Movie> findLast10() {
        return movieRepository.findLast10();
    }
    public void importFromCSV(MultipartFile multipartFile) throws IOException {
        if(!multipartFile.getContentType().equals("text/csv"))
        {
            return;
        }
        Reader reader = new InputStreamReader(multipartFile.getInputStream());
        List<ImportDto> beans =  new CsvToBeanBuilder(reader)
                .withType(ImportDto.class).build().parse();
        saveMovies(mapToMovies(beans));
    }

    private List<Movie> mapToMovies(List<ImportDto> beans) {
        List<Movie> movies = new ArrayList<>();
        beans.stream().forEach(bean -> {
            Movie movie = new Movie();
            if(bean.getId() != null)
                movie.setId(bean.getId());

            if(bean.getExternalId() != null)
                movie.setExternalId(bean.getExternalId());

            if(bean.getTitle() != null)
                movie.setTitle(bean.getTitle());

            if(bean.getOverview() != null)
                movie.setOverview(bean.getOverview());

            if(bean.getPosterPath() != null)
                movie.setPosterPath(bean.getPosterPath());

            if(bean.getPopularity() != null)
                movie.setPopularity(bean.getPopularity());

            if(bean.getReleaseDate() != null)
                movie.setReleaseDate(bean.getReleaseDate());

            if(bean.getVideo() != null)
                movie.setVideo(bean.getVideo());

            if(bean.getVoteAverage() != null)
                movie.setVoteAverage(bean.getVoteAverage());

            if(bean.getVoteCount() != null)
                movie.setVoteCount(bean.getVoteCount());

            movies.add(movie);
        });

        return movies;
    }

    private List<ExportDto> mapToExportDto(List<Movie> movies) {
        List<ExportDto> exportDtos = new ArrayList<>();
        movies.forEach(movie -> {
            ExportDto exportDto = new ExportDto();
            exportDto.setId(movie.getId());
            exportDto.setExternalId(movie.getExternalId());
            exportDto.setTitle(movie.getTitle());
            exportDto.setOverview(movie.getOverview());
            exportDto.setPosterPath(movie.getPosterPath());
            exportDto.setMediaType(movie.getMediaType());
            exportDto.setPopularity(movie.getPopularity());
            exportDto.setReleaseDate(movie.getReleaseDate());
            exportDto.setVideo(movie.getVideo());
            exportDto.setVoteAverage(movie.getVoteAverage());
            exportDto.setVoteCount(movie.getVoteCount());
            exportDtos.add(exportDto);
        });
        return exportDtos;
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
