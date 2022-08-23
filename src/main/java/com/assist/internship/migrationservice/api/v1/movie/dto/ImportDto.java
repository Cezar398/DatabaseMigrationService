package com.assist.internship.migrationservice.api.v1.movie.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ImportDto {
    @CsvBindByName(column = "id", required = true)
    private Long id;
    @CsvBindByName(column = "externalId")
    private String externalId;
    @CsvBindByName(column = "title", required = true)
    private String title;
    @CsvBindByName(column = "overview")
    private String overview;
    @CsvBindByName(column = "posterPath")
    private String posterPath;
    @CsvBindByName(column = "mediaType")
    private String mediaType;
    @CsvBindByName(column = "popularity")
    private String popularity;
    @CsvBindByName(column = "releaseDate")
    private String releaseDate;
    @CsvBindByName(column = "video")
    private Boolean video;
    @CsvBindByName(column = "voteAverage")
    private Float voteAverage;
    @CsvBindByName(column = "voteCount")
    private Integer voteCount;
}
