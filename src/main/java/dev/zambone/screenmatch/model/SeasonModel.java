package dev.zambone.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonModel(@JsonAlias("Season") Integer season,
                          @JsonAlias("Episodes") List<EpisodeModel> episodes) {
}
