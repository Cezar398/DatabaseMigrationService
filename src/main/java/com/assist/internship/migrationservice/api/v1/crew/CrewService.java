package com.assist.internship.migrationservice.api.v1.crew;

import com.assist.internship.migrationservice.api.v1.crew.dto.CrewDataDto;
import com.assist.internship.migrationservice.api.v1.crew.dto.CrewInfoDto;
import com.assist.internship.migrationservice.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class CrewService {
    private final CrewRepository crewRepository;
    private final String CREW_NOT_FOUND = "Member not found";

    public List<CrewInfoDto> getAll() {
        return crewRepository.findAll().stream().map(crew -> CrewInfoDto
                        .builder()
                        .id(crew.getId())
                        .firstName(crew.getFirstName())
                        .lastName(crew.getLastName())
                        .birthDate(crew.getBirthDate())
                        .build())
                .collect(Collectors.toList());
    }

    public Member getById(Long id) {
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

    public Member updateById(Long id, CrewDataDto crewDataDto) {
        Member oldCrew = getById(id);
        Member newCrew = mapDtoToCrew(crewDataDto);
        newCrew.setId(oldCrew.getId());
        return crewRepository.save(newCrew);
    }

    public void save(Member crew) {
        crewRepository.save(crew);
    }

    private Member mapDtoToCrew(CrewDataDto crewDataDto) {
        Member crew = new Member();
        crew.setFirstName(crewDataDto.getFirstName());
        crew.setLastName(crewDataDto.getLastName());
        crew.setBirthDate(crewDataDto.getBirthDate());
        return crew;
    }
}
