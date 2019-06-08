package com.dispose.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dispose.model.Result;
import com.dispose.model.Skill;
import com.dispose.model.User;

public interface ResultRepository extends JpaRepository<Result, Long>{
	Optional<List<Result>> findByCandidate(User user);
	Optional<List<Result>> findBySkill(Skill skill);
	Optional<Result> findBySkillIdAndCandidateId(Long skillId, Long userId);
	Optional<List<Result>> findBySkillOrderByRespostesCorrectesDesc(Skill skill);
}
