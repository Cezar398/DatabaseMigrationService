package com.assist.internship.migrationservice.api.v1.member;

import com.assist.internship.migrationservice.api.v1.member.dto.MemberDataDto;
import com.assist.internship.migrationservice.api.v1.member.dto.MemberInfoDto;
import com.assist.internship.migrationservice.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/crew")
@Tag(description = "Member resources that provides access to available crew", name = "Member Resource")
public class MemberController {
    private final MemberService memberService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All crew have been selected"),
            @ApiResponse(responseCode = "404", description = "No crews found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Get all crew from database", summary = "Get all")
    @GetMapping
    public List<MemberInfoDto> getAll() {
        return memberService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member have been selected"),
            @ApiResponse(responseCode = "404", description = "No crew found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Get crew by id from database", summary = "Get crew")
    @GetMapping("/{id}")
    public Member getById(@Parameter(description = "Member ID", example = "3") @PathVariable("id") Long id) {
        return memberService.getById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member has been created"),
            @ApiResponse(responseCode = "404", description = "Member has not been created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Create crew", summary = "Create crew")
    @PostMapping
    public void create(@ParameterObject MemberDataDto memberDataDto) {
        memberService.create(memberDataDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All crews has been deleted"),
            @ApiResponse(responseCode = "404", description = "Crews not deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Delete all crews from database", summary = "Delete all")
    @DeleteMapping
    public void delete() {
        memberService.delete();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member has been deleted"),
            @ApiResponse(responseCode = "404", description = "Member not deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Delete crew from database", summary = "Delete crew")
    @DeleteMapping("/{id}")
    public void deleteById(@Parameter(description = "Id for crew to be deleted", example = "3") @PathVariable("id") Long id) {
        memberService.deleteById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member has been updated"),
            @ApiResponse(responseCode = "404", description = "Member not updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Update crew from database", summary = "Update crew")
    @PutMapping("/{id}")
    public Member updateById(@Parameter(description = "Id for crew to be updated") @PathVariable("id") Long id, @ParameterObject MemberDataDto memberDataDto) {
        return memberService.updateById(id, memberDataDto);
    }
}
