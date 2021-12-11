package com.matchus.domains.location.repository;

import com.matchus.domains.location.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

}
