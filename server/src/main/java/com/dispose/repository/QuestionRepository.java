package com.dispose.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dispose.model.Question;
import com.dispose.model.Skill;
import com.dispose.model.User;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	Optional<List<Question>> findByQuestionUser(User user);
	Optional<List<Question>> findBySkill(Skill skill);
	Optional<List<Question>> findTop10BySkill(Skill skill);
	
	@Query(value="select * from question q where q.skill_id = :#{#skill} order by rand() limit 10", nativeQuery = true)
	Optional<List<Question>> findTop10BySkillOrderByRand(@Param("skill") long skillId);
}
