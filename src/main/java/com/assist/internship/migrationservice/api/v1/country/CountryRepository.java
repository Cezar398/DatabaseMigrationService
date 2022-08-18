package com.assist.internship.migrationservice.api.v1.country;

import com.assist.internship.migrationservice.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}

