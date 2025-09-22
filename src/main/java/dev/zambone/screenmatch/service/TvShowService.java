package dev.zambone.screenmatch.service;

import dev.zambone.screenmatch.dto.EpisodeDTO;
import dev.zambone.screenmatch.dto.TvShowDTO;
import dev.zambone.screenmatch.model.Category;
import dev.zambone.screenmatch.model.TvShow;
import dev.zambone.screenmatch.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TvShowService {

    @Autowired
    private TvShowRepository tvShowRepository;

    public List<TvShowDTO> getAllTvShows() {
        return convertToDto(tvShowRepository.findAll());

    }

    public List<TvShowDTO> getTop5TvShows() {
        return convertToDto(tvShowRepository.findTop5ByOrderByRatingDesc());
    }

    private List<TvShowDTO> convertToDto(List<TvShow> tvShows) {
        return tvShows.stream()
                .map(t -> new TvShowDTO(t.getId(), t.getTitle(), t.getTotalSeasons(), t.getRating(), t.getGenre(), t.getActors(), t.getPoster(), t.getSynopsis()))
                .collect(Collectors.toList());
    }

    public List<TvShowDTO> getLatestReleases() {
        return convertToDto(tvShowRepository.latestReleases());
    }

    public TvShowDTO getTvShowById(Long id) {
        Optional<TvShow> tvShow = tvShowRepository.findById(id);
        if (tvShow.isPresent()) {
            TvShow t = tvShow.get();
            return new TvShowDTO(t.getId(), t.getTitle(), t.getTotalSeasons(), t.getRating(), t.getGenre(), t.getActors(), t.getPoster(), t.getSynopsis());
        }
        return null;
    }

    public List<EpisodeDTO> getAllSeasons(Long id) {
        Optional<TvShow> tvShow = tvShowRepository.findById(id);

        if (tvShow.isPresent()) {
            TvShow t = tvShow.get();
            return t.getEpisodes().stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodeDTO> getSeasonByNumber(Long id, Integer seasonNumber) {
        return tvShowRepository.getEpisodeBySeason(id, seasonNumber)
                .stream()
                .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                .collect(Collectors.toList());
    }

    public List<TvShowDTO> getTvShowsByGenre(String genre) {
        Category category = Category.fromString(genre);
        return convertToDto(tvShowRepository.findByGenre(category));
    }
}
