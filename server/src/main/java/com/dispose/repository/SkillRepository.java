package com.dispose.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dispose.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

	Optional<Skill> findBySkillName(String skill);
	Optional<Skill> findBySkillId(String skillId);
	boolean existsBySkillName(String skill);
	
}
