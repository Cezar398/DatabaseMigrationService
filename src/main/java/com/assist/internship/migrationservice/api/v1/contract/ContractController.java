package com.assist.internship.migrationservice.api.v1.contract;

import com.assist.internship.migrationservice.api.v1.contract.dto.ContractDataDto;
import com.assist.internship.migrationservice.api.v1.contract.dto.ContractInfoDto;
import com.assist.internship.migrationservice.entity.Contract;
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
@RequestMapping(value = "api/v1/contract")
@Tag(description = "Contract operations", name = "Contract")
public class ContractController {
    private final ContractService contractService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All contracts have been selected"),
            @ApiResponse(responseCode = "404", description = "No contracts found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Get all contracts from database", summary = "Get all")
    @GetMapping
    public List<ContractInfoDto> getAll()
    {
        return contractService.getAll();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract have been selected"),
            @ApiResponse(responseCode = "404", description = "No contract found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Get contract from database", summary = "Get by id")
    public Contract getById(@Parameter(description = "Contract id", example = "1") @PathVariable Long id){
        return contractService.getById(id);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract have been created"),
            @ApiResponse(responseCode = "404", description = "No contract created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Create new contract to the database", summary = "Create contract")
    public void create(@ParameterObject ContractDataDto contractDataDto)
    {
        contractService.create(contractDataDto);
    }

}
