package org.dim4es.springserver.service.country;

import org.dim4es.springserver.dto.CountryDto;

import java.util.List;

public interface CountryService {

    List<CountryDto> searchByName(String name);

    List<CountryDto> getAll();
}
