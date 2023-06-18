package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.CountryDto;
import org.dim4es.springserver.service.country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final CountryService countryService;

    @Autowired
    public DictionaryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDto>> searchCountriesByName(@RequestParam(required = false) String name) {
        List<CountryDto> countries;
        if (name != null) {
            countries = countryService.searchByName(name);
        } else {
            countries = countryService.getAll();
        }
        return ResponseEntity.ok(countries);
    }
}
