package com.assist.internship.migrationservice.api.v1.crew;

import com.assist.internship.migrationservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRepository extends JpaRepository<Member, Long> {
}
