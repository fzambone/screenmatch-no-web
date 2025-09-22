package dev.zambone.screenmatch.dto;

import dev.zambone.screenmatch.model.Category;

public record TvShowDTO(
        Long id,
        String title,
        Integer totalSeasons,
        Double rating,
        Category genre,
        String actors,
        String poster,
        String synopsis
) {
}
