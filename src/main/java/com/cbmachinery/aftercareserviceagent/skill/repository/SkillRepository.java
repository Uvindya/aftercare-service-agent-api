package com.cbmachinery.aftercareserviceagent.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.skill.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
