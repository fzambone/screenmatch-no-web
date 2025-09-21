package dev.zambone.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvShowModel(@JsonAlias("Title") String title,
                          @JsonAlias("totalSeasons") Integer totalSeasons,
                          @JsonAlias("imdbRating") String rating,
                          @JsonAlias("Genre") String genre,
                          @JsonAlias("Actors") String actors,
                          @JsonAlias("Poster") String poster,
                          @JsonAlias("Plot") String synopsis

) {
}
