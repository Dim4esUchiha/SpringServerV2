package org.dim4es.springserver.services.country;

import org.dim4es.springserver.dto.CountryDto;
import org.dim4es.springserver.models.Country;
import org.dim4es.springserver.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountryServiceImpl implements CountryService {

    private static final int MAX_COUNTRIES_SIZE_FOR_SEARCH = 10;

    private final CountryRepository countryRepository;

    private final Function<Country, CountryDto> countryMapper = country ->
            new CountryDto(country.getId(), country.getName());

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryDto> searchByName(String name) {
        String searchToken = name + "%";

        Pageable pageRequest = PageRequest.of(0, MAX_COUNTRIES_SIZE_FOR_SEARCH);
        List<Country> countries = countryRepository.searchByName(searchToken, pageRequest);
        return countries.stream().map(countryMapper).collect(Collectors.toList());
    }

    @Override
    public List<CountryDto> getAll() {
        Iterable<Country> countries = countryRepository.findAll();
        return StreamSupport.stream(countries.spliterator(), false)
                .map(countryMapper).collect(Collectors.toList());
    }
}
