package com.assist.internship.migrationService.api.v1.contract;

import com.assist.internship.migrationservice.api.v1.contract.ContractController;
import com.assist.internship.migrationservice.api.v1.contract.ContractService;
import com.assist.internship.migrationservice.entity.Contract;
import com.assist.internship.migrationservice.entity.Member;
import com.assist.internship.migrationservice.entity.Movie;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContractController.class)
public class ContractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @Test
    void getAll_ShouldGetAllContractsFromDatabase() throws Exception{
        Contract contract = getContract1();

        List<Contract> contractList = Arrays.asList(contract);

        Mockito.when(contractService.getAll()).thenReturn(contractList);

        mockMvc.perform(get("/api/v1/contract"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].startDate", Matchers.is("2000-10-01")))
                .andExpect(jsonPath("$[0].endDate", Matchers.is("2001-10-01")));
    }

    @Test
    void getAll_ShouldGetAllContractsFromDatabaseWithCorrectDateFormat() throws Exception{
        Contract contract = getContract2();

        List<Contract> contractList = Arrays.asList(contract);

        Mockito.when(contractService.getAll()).thenReturn(contractList);

        mockMvc.perform(get("/api/v1/contract"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].startDate", Matchers.is("2000-10-01")))
                .andExpect(jsonPath("$[0].endDate", Matchers.is("2001-10-01")));
    }

    @Test
    void getAll_ShouldGetAllContractsFromDatabaseWithCorrectDayFormat() throws Exception{
        Contract contract = getContract3();

        List<Contract> contractList = Arrays.asList(contract);

        Mockito.when(contractService.getAll()).thenReturn(contractList);

        mockMvc.perform(get("/api/v1/contract"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].startDate", Matchers.is("2000-03-01")))
                .andExpect(jsonPath("$[0].endDate", Matchers.is("2001-03-02")));
    }

    @Test
    void getAll_ShouldGetAllContractsFromDatabaseWithCorrectMonthFormat() throws Exception{
        Contract contract = getContract3();

        List<Contract> contractList = Arrays.asList(contract);

        Mockito.when(contractService.getAll()).thenReturn(contractList);

        mockMvc.perform(get("/api/v1/contract"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].startDate", Matchers.is("2000-03-01")))
                .andExpect(jsonPath("$[0].endDate", Matchers.is("2001-03-02")));
    }

    @Test
    void getById_ShouldGetAContractById() throws Exception
    {
        Contract contract = getContract1();

        Mockito.when(contractService.getById(any(Long.class))).thenReturn(contract);

        mockMvc.perform(get("/api/v1/contract/1"))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.startDate", Matchers.is("2000-10-01")))
                .andExpect(jsonPath("$.endDate", Matchers.is("2001-10-01")));

    }

    private Contract getContract1(){
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setStartDate(Date.valueOf("2000-10-01"));
        contract.setEndDate(Date.valueOf("2001-10-01"));
        contract.setMovie(new Movie());
        contract.setMember(new Member());
        return contract;
    }

    private Contract getContract2(){
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setStartDate(Date.valueOf("2000-10-1"));
        contract.setEndDate(Date.valueOf("2001-10-1"));
        contract.setMovie(new Movie());
        contract.setMember(new Member());
        return contract;
    }

    private Contract getContract3(){
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setStartDate(Date.valueOf("2000-2-30"));
        contract.setEndDate(Date.valueOf("2001-2-30"));
        contract.setMovie(new Movie());
        contract.setMember(new Member());
        return contract;
    }

    private Contract getContract4(){
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setStartDate(Date.valueOf("2000-13-30"));
        contract.setEndDate(Date.valueOf("2001-13-30"));
        contract.setMovie(new Movie());
        contract.setMember(new Member());
        return contract;
    }
}
