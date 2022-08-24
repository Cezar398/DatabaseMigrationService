package com.assist.internship.migrationservice.api.v1.crew;

import com.assist.internship.migrationservice.api.v1.crew.dto.CreateDto;
import com.assist.internship.migrationservice.api.v1.exception.CrewException.CrewApiException;
import com.assist.internship.migrationservice.entity.Crew;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CrewService {
    private final CrewRepository crewRepository;
    private final String CREW_NOT_FOUND = "Crew not found";

    public List<Crew> getAll()
    {
        return crewRepository.findAll();
    }

    public Crew getById(Long id)
    {
        return crewRepository.findById(id).orElseThrow(() -> new CrewApiException(CREW_NOT_FOUND));
    }

    public void create(CreateDto createDto)
    {
        crewRepository.save(createDtoToCrew(createDto));
    }

    private Crew createDtoToCrew(CreateDto createDto)
    {
        Crew crew = new Crew();
        crew.setFirstName(createDto.getFirstName());
        crew.setLastName(createDto.getLastName());
        crew.setBirthDate(createDto.getBirthDate());
        return crew;
    }
}
