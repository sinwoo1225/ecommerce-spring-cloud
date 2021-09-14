package siru.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siru.catalogservice.entity.CatalogEntity;
import siru.catalogservice.service.CatalogService;
import siru.catalogservice.vo.ResponseCatalog;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

    private final Environment env;
    private final ModelMapper modelMapper;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It`s Working on Catalog Service on Port %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

        List<ResponseCatalog> result = new ArrayList<>();
        catalogList.forEach(catalog -> {
            result.add(modelMapper.map(catalog, ResponseCatalog.class));
        });

        return ResponseEntity.ok().body(result);
    }
}
