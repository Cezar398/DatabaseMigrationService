package com.assist.internship.migrationservice.api.v1.member;

import com.assist.internship.migrationservice.api.v1.member.dto.MemberDataDto;
import com.assist.internship.migrationservice.api.v1.member.dto.MemberInfoDto;
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
public class MemberService {
    private final MemberRepository memberRepository;
    private final String CREW_NOT_FOUND = "Member not found";

    public List<MemberInfoDto> getAll() {
        return memberRepository.findAll().stream().map(crew -> MemberInfoDto
                        .builder()
                        .id(crew.getId())
                        .firstName(crew.getFirstName())
                        .lastName(crew.getLastName())
                        .birthDate(crew.getBirthDate())
                        .build())
                .collect(Collectors.toList());
    }

    public Member getById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CREW_NOT_FOUND));
    }

    public void create(MemberDataDto memberDataDto) {
        memberRepository.save(mapDtoToCrew(memberDataDto));
    }

    public void delete() {
        memberRepository.deleteAll();
    }

    public void deleteById(Long id) {
        memberRepository.delete(getById(id));
    }

    public Member updateById(Long id, MemberDataDto memberDataDto) {
        Member oldCrew = getById(id);
        Member newCrew = mapDtoToCrew(memberDataDto);
        newCrew.setId(oldCrew.getId());
        return memberRepository.save(newCrew);
    }

    public void save(Member crew) {
        memberRepository.save(crew);
    }

    private Member mapDtoToCrew(MemberDataDto memberDataDto) {
        Member crew = new Member();
        crew.setFirstName(memberDataDto.getFirstName());
        crew.setLastName(memberDataDto.getLastName());
        crew.setBirthDate(memberDataDto.getBirthDate());
        return crew;
    }
}
