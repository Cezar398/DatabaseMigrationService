package com.assist.internship.migrationservice.api.v1.contract;

import com.assist.internship.migrationservice.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
