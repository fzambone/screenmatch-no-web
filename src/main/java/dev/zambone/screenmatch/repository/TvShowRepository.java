package dev.zambone.screenmatch.repository;

import dev.zambone.screenmatch.model.Category;
import dev.zambone.screenmatch.model.Episode;
import dev.zambone.screenmatch.model.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    Optional<TvShow> findByTitleContainingIgnoreCase(String title);

    List<TvShow> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actors, Double rating);

    List<TvShow> findTop5ByOrderByRatingDesc();

    List<TvShow> findByGenre(Category genre);

    List<TvShow> findByTotalSeasonsIsLessThanEqualAndRatingGreaterThanEqual(Integer totalSeasons, Double rating);

    @Query("SELECT t FROM TvShow t WHERE t.totalSeasons <= :totalSeasons AND t.rating >= :rating")
    List<TvShow> tvShowBySeasonsAndRating(Integer totalSeasons, Double rating);

    @Query("SELECT e FROM TvShow t JOIN t.episodes e WHERE e.title ILIKE %:snippet%")
    List<Episode> episodeBySnippet(String snippet);

    @Query("SELECT e FROM TvShow t JOIN t.episodes e WHERE t = :foundTvShow ORDER BY e.rating DESC LIMIT 5")
    List<Episode> topEpisodesByTvShow(TvShow foundTvShow);


    @Query("SELECT t FROM TvShow t " +
            "JOIN t.episodes e " +
            "GROUP BY t " +
            "ORDER BY MAX(e.releaseDate) DESC LIMIT 5")
    List<TvShow> latestReleases();

    @Query("SELECT e FROM TvShow t JOIN t.episodes e WHERE t.id = :id AND e.season = :season")
    List<Episode> getEpisodeBySeason(Long id, Integer season);
}
