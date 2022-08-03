package entity;

import lombok.Data;

import java.util.Date;

@Data
public class Migration {
    private String id;
    private String title;
    private String overview;
    private String poster_path;
    private String media_type;
    private String popularity;
    private Date release_date;
    private boolean video;
    private float vote_average;
    private int vote_count;
}
