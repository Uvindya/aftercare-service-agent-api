package com.cbmachinery.aftercareserviceagent.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;

public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {

}
