package dev.zambone.screenmatch.main;

import dev.zambone.screenmatch.model.*;
import dev.zambone.screenmatch.repository.TvShowRepository;
import dev.zambone.screenmatch.service.JsonDataConverter;
import dev.zambone.screenmatch.service.RequestApi;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private TvShowRepository tvShowRepository;
    private Scanner scanner = new Scanner(System.in);
    private RequestApi requestApi = new RequestApi();
    private JsonDataConverter converter = new JsonDataConverter();
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b75c3faa";
    private List<TvShowModel> tvShowsData = new ArrayList<>();
    private List<TvShow> tvShows = new ArrayList<>();
    private Optional<TvShow> episodeByTvShow = Optional.empty();
    

    public Main(TvShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    public void showMenu() {
        var menu = """
                1 - Search TV Shows
                2 - Search Episodes
                3 - List searched Tv Shows
                4 - Search Tv Show by Title
                5 - Search Tv Show by Actor
                6 - Top 5 Tv Shows
                7 - Search Tv Show by Category
                8 - Search Tv Show by Max Seasons
                9 - Search Episode by Snippet
                10 - Top 5 Episodes by TvShow
                
                0 - Exit
                """;

        var option = -1;
        while (option != 0) {
        System.out.println(menu);
        option = scanner.nextInt();
        scanner.nextLine();
            switch (option) {
                case 1:
                    searchTvShow();
                    break;
                case 2:
                    searchEpisodeByTvShow();
                    break;
                case 3:
                    listSearchedTvShows();
                    break;
                case 4:
                    searchTvShowByTitle();
                    break;
                case 5:
                    searchTvShowByActor();
                    break;
                case 6:
                    searchTop5TvShows();
                    break;
                case 7:
                    searchTvShowByCategory();
                    break;
                case 8:
                    searchTvShowByMaxSeasons();
                    break;
                case 9 :
                    searchEpisodeBySnippet();
                    break;
                case 10:
                    searchTopEpisodesByTvShow();
                    break;
                case 0:
                    System.out.println("Bye bye...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void searchTvShow() {
        TvShowModel tvShowData = getTvShowData();
        TvShow tvShow = new TvShow(tvShowData);

        tvShowRepository.save(tvShow);
        System.out.println(tvShowData);
    }

    private TvShowModel getTvShowData() {
        System.out.println("Enter the name of the tv show: ");
        var tvShowName = scanner.nextLine();
        var json = requestApi.getData(URL + tvShowName.replace(" ", "+") + API_KEY);
        return converter.getData(json, TvShowModel.class);
    }

    private void searchEpisodeByTvShow() {
        listSearchedTvShows();
        System.out.println("Choose a tv show: ");
        var tvShowName = scanner.nextLine();

        episodeByTvShow = tvShowRepository.findByTitleContainingIgnoreCase(tvShowName);
        if(episodeByTvShow.isPresent()) {
            var foundTvShow = episodeByTvShow.get();
            List<SeasonModel> seasons = new ArrayList<>();

            for (int i = 1; i <= foundTvShow.getTotalSeasons(); i++) {
                var json = requestApi.getData(URL + foundTvShow.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonModel seasonData = converter.getData(json, SeasonModel.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.season(), e)))
                    .collect(Collectors.toList());

            foundTvShow.setEpisodes(episodes);
            tvShowRepository.save(foundTvShow);
        } else {
            System.out.println("Tv Show not found in the database");
        }
    }

    private void listSearchedTvShows() {
        tvShows = tvShowRepository.findAll();
        tvShows.stream()
                .sorted(Comparator.comparing(TvShow::getGenre))
                .forEach(System.out::println);
    }

    private void searchTvShowByTitle() {
        System.out.println("Enter the title of the tv show: ");
        var tvShowName = scanner.nextLine();
        Optional<TvShow> searchedTvShow = tvShowRepository.findByTitleContainingIgnoreCase(tvShowName);

        if (searchedTvShow.isPresent()) {
            System.out.println("Tv Show data: " + searchedTvShow.get());
        } else {
            System.out.println("Tv Show not found in the database");
        }
    }

    private void searchTvShowByActor() {
        System.out.println("Enter the name of the tv show: ");
        var actorName = scanner.nextLine();
        System.out.println("Enter the minimum rating for the tv show: ");
        var rating = scanner.nextDouble();
        List<TvShow> foundTvShows = tvShowRepository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actorName,rating);
        System.out.println("Tv Show data: ");
        foundTvShows.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void searchTop5TvShows() {
        List<TvShow> topTvShows = tvShowRepository.findTop5ByOrderByRatingDesc();
        topTvShows.forEach(t -> System.out.println(t.getTitle() + " rating: " + t.getRating()));
    }

    private void searchTvShowByCategory() {
        System.out.println("Enter the category of the tv show: ");
        var category = scanner.nextLine().toUpperCase();
        List<TvShow> tvShowByCategory = tvShowRepository.findByGenre(Category.valueOf(category));
        tvShowByCategory.forEach(s -> System.out.println(s.getTitle() + " genre: " + s.getGenre()));
    }

    private void searchTvShowByMaxSeasons() {
        System.out.println("Enter the max seasons of the tv show: ");
        var maxSeasons = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the minimum rating for the tv show: ");
        var rating = scanner.nextDouble();
        scanner.nextLine();
        List<TvShow> tvShowByMaxSeasons = tvShowRepository.tvShowBySeasonsAndRating(maxSeasons,rating);
        tvShowByMaxSeasons.forEach(s -> System.out.println(s.getTitle() + ", seasons: " + s.getTotalSeasons() + " rating: " + s.getRating()));
    }

    private void searchEpisodeBySnippet() {
        System.out.println("Enter the snippet of the episode title: ");
        var snippet = scanner.nextLine();
        List<Episode> episodesBySnippet = tvShowRepository.episodeBySnippet(snippet);
        episodesBySnippet.forEach(e -> System.out.println(e.getTitle() + "Season: " + e.getSeason() + " rating: " + e.getRating()));
    }

    private void searchTopEpisodesByTvShow() {
        searchEpisodeByTvShow();
        if (episodeByTvShow.isPresent()) {
            var foundTvShow = episodeByTvShow.get();
            List<Episode> topEpisodes = tvShowRepository.topEpisodesByTvShow(foundTvShow);
            topEpisodes.forEach(e ->
                    System.out.printf("TvShow: %s, Season: %s - Episode %s - %s\n",
                            e.getTvShow().getTitle(), e.getSeason(),
                            e.getEpisodeNumber(), e.getTitle()));
        }
    }
}