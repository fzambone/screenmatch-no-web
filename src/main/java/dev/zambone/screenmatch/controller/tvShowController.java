package dev.zambone.screenmatch.controller;

import dev.zambone.screenmatch.dto.EpisodeDTO;
import dev.zambone.screenmatch.dto.TvShowDTO;
import dev.zambone.screenmatch.service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/tv-shows")
@RestController
public class tvShowController {

    @Autowired
    private TvShowService tvShowService;

    @GetMapping
    public List<TvShowDTO> getTvShows() {
        return tvShowService.getAllTvShows();
    }

    @GetMapping("/top5")
    public List<TvShowDTO> getTop5TvShows() {
        return tvShowService.getTop5TvShows();
    }

    @GetMapping("/latest-releases")
    public List<TvShowDTO> getLatestReleaseTvShows() {
        return tvShowService.getLatestReleases();
    }

    @GetMapping("/{id}")
    public TvShowDTO getTvShow(@PathVariable Long id) {
        return tvShowService.getTvShowById(id);
    }

    @GetMapping("/{id}/seasons/all")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id){
        return tvShowService.getAllSeasons(id);
    }

    @GetMapping("/{id}/seasons/{seasonNumber}")
    public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Integer seasonNumber){
        return tvShowService.getSeasonByNumber(id, seasonNumber);
    }

    @GetMapping("/category/{genre}")
    public List<TvShowDTO> getTvShowsByGenre(@PathVariable String genre){
        return tvShowService.getTvShowsByGenre(genre);
    }
}
