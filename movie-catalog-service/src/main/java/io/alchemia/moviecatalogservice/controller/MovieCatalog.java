package io.alchemia.moviecatalogservice.controller;

import io.alchemia.moviecatalogservice.model.CatalogItem;
import io.alchemia.moviecatalogservice.service.MovieCatalogService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalog {

    private final MovieCatalogService movieCatalogService;

    public MovieCatalog(final MovieCatalogService movieCatalogService) {
        this.movieCatalogService = movieCatalogService;
    }

    @RequestMapping("/{id}")
    public List<CatalogItem> getCatalog(@PathVariable("id") String id) {
        return movieCatalogService.getCatalog(id);
    }
}
