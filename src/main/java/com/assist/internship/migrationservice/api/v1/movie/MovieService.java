package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSearchCriteria;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSpecification;
import com.assist.internship.migrationservice.config.properties.ExportConfig;
import com.assist.internship.migrationservice.config.properties.ImportConfig;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void saveMovies(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType(exportConfig.getContentType());
        response.setHeader(exportConfig.getHeaderKey(), exportConfig.getHeaderValue() + "_movie" + exportConfig.getFileFormat());

        PrintWriter printWriter = new PrintWriter(response.getWriter());

        CSVPrinter csvPrinter = new CSVPrinter(printWriter, CSVFormat.EXCEL.withHeader());

        List<Movie> movies = findAll();

        movies.forEach(movie -> {
            try {
                csvPrinter.printRecord(movie.getId(),
                        movie.getExternalId(),
                        movie.getTitle(),
                        movie.getOverview(),
                        movie.getPosterPath(),
                        movie.getMediaType(),
                        movie.getPopularity(),
                        movie.getReleaseDate(),
                        movie.getVideo(),
                        movie.getVoteAverage(),
                        movie.getVoteCount());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        csvPrinter.flush();
    }
    public void uploadCSV(MultipartFile multipartFile) throws IOException {
        File csvFile = convertTempToCSV(multipartFile);
        CSVParser csvParser = new CSVParser(new FileReader(csvFile.getPath()), CSVFormat.DEFAULT);
        List<CSVRecord> csvRecords = csvParser.getRecords();
        List<Movie> movies = new ArrayList<>();
        csvRecords.stream().skip(1).forEach(i -> {
            Movie movie = getMovie(i);
            movies.add(movie);
        });
        csvParser.close();
        saveMovies(movies);
    }

    private static Movie getMovie(CSVRecord i) {
        Movie movie = new Movie();
        movie.setExternalId(i.get(1));
        movie.setTitle(i.get(2));
        movie.setOverview(i.get(3));
        movie.setPosterPath(i.get(4));
        movie.setMediaType((i.get(5)));
        movie.setPopularity(i.get(6));
        movie.setReleaseDate(i.get(7));
        movie.setVideo(Boolean.valueOf(i.get(8)));
        movie.setVoteAverage(Float.valueOf(i.get(9)));
        movie.setVoteCount(Integer.valueOf(i.get(10)));
        return movie;
    }

    private File convertTempToCSV(MultipartFile multipartFile) throws IOException {
        String baseUrl = importConifg.getBaseUrl();
        String fullUrl = httpServletRequest.getServletContext().getRealPath(baseUrl);

        if(!new File(fullUrl).exists())
        {
            new File(fullUrl).mkdir();
        }

        String fileOriginalName = multipartFile.getOriginalFilename();
        String filePath = fullUrl + fileOriginalName;
        File fileDestination = new File(filePath);
        multipartFile.transferTo(fileDestination);

        return fileDestination;
    }

    public List<Movie> findLast10() {
        return movieRepository.findLast10();
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
