package dev.zambone.screenmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "tvShows")
public class TvShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    private Integer totalSeasons;
    private Double rating;
    @Enumerated(EnumType.STRING)
    private Category genre;
    private String actors;
    private String poster;
    private String synopsis;
    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodes = new ArrayList<>();

    public TvShow(TvShowModel tvShowModel) {
        this.title = tvShowModel.title();
        this.totalSeasons = tvShowModel.totalSeasons();
        this.rating = OptionalDouble.of(Double.valueOf(tvShowModel.rating())).orElse(0.0);
        this.genre = Category.fromString(tvShowModel.genre().split(",")[0].trim());
        this.actors = tvShowModel.actors();
        this.poster = tvShowModel.poster();
        this.synopsis = tvShowModel.synopsis();
    }

    public TvShow(Long id) {
        this.id = id;
    }

    public TvShow() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(episode -> episode.setTvShow(this));
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return
                "genre=" + genre +
                        ", title='" + title + '\'' +
                        ", totalSeasons=" + totalSeasons +
                        ", rating=" + rating +
                        ", actors='" + actors + '\'' +
                        ", poster='" + poster + '\'' +
                        ", synopsis='" + synopsis + '\'' +
                        ", episodes='" + episodes + '\'';
    }
}