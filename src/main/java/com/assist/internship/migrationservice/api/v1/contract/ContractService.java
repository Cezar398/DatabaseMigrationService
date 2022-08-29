package com.assist.internship.migrationservice.api.v1.contract;

import com.assist.internship.migrationservice.api.v1.contract.dto.ContractDataDto;
import com.assist.internship.migrationservice.api.v1.contract.dto.ContractInfoDto;
import com.assist.internship.migrationservice.api.v1.crew.CrewService;
import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.entity.Contract;
import com.assist.internship.migrationservice.entity.Member;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final MovieService movieService;
    private final CrewService crewService;

    private final String CONTRACT_NOT_FOUND = "Contract not found!";

    public List<ContractInfoDto> getAll() {
        return contractRepository.findAll().stream().map(contract -> ContractInfoDto
                        .builder()
                        .id(contract.getId())
                        .startDate(contract.getStartDate())
                        .endDate(contract.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }

    public Contract getById(Long id) {
        return contractRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTRACT_NOT_FOUND));
    }

    public void create(ContractDataDto contractDataDto) {
        Movie movie = movieService.findById(contractDataDto.getMovieId());
        Member crew = crewService.getById(contractDataDto.getCrewId());
        Contract contract = mapToContract(contractDataDto, movie, crew);
        contractRepository.save(contract);
    }

    private Contract mapToContract(ContractDataDto contractDataDto, Movie movie, Member crew) {
        Contract contract = new Contract();
        contract.setMovie(movie);
        contract.setMember(crew);
        contract.setStartDate(contractDataDto.getStartDate());
        contract.setEndDate(contractDataDto.getEndDate());
        return contract;
    }
}
