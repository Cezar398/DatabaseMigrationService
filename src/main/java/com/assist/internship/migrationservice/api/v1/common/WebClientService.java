package com.assist.internship.migrationservice.api.v1.common;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WebClientService {
    private final WebClient webClient;
    private final WebClient movieIdsWebClient;

    public MovieResponse getMovie(String url) {
        return webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(MovieResponse.class)
                .block();
    }
    public List<String> getIds(String url)
    {
        return movieIdsWebClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}
