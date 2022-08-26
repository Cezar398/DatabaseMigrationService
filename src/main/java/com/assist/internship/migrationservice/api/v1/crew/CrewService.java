package com.assist.internship.migrationservice.api.v1.crew;

import com.assist.internship.migrationservice.api.v1.crew.dto.CrewDataDto;
import com.assist.internship.migrationservice.entity.Crew;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CrewService {
    private final CrewRepository crewRepository;
    private final String CREW_NOT_FOUND = "Crew not found";

    public List<Crew> getAll() {
        return crewRepository.findAll();
    }

    public Crew getById(Long id) {
        return crewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CREW_NOT_FOUND));
    }

    public void create(CrewDataDto crewDataDto) {
        crewRepository.save(mapDtoToCrew(crewDataDto));
    }

    public void delete() {
        crewRepository.deleteAll();
    }

    public void deleteById(Long id) {
        crewRepository.delete(getById(id));
    }

    public Crew updateById(Long id, CrewDataDto crewDataDto) {
        Crew oldCrew = getById(id);
        Crew newCrew = mapDtoToCrew(crewDataDto);
        newCrew.setId(oldCrew.getId());
        return crewRepository.save(newCrew);
    }

    private Crew mapDtoToCrew(CrewDataDto crewDataDto) {
        Crew crew = new Crew();
        crew.setFirstName(crewDataDto.getFirstName());
        crew.setLastName(crewDataDto.getLastName());
        crew.setBirthDate(crewDataDto.getBirthDate());
        return crew;
    }
}
