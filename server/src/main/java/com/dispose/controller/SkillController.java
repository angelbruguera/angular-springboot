package com.dispose.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dispose.model.Question;
import com.dispose.model.Result;
import com.dispose.model.Skill;
import com.dispose.payload.ApiResponse;
import com.dispose.payload.SkillRequest;
import com.dispose.payload.SkillResponse;
import com.dispose.repository.QuestionRepository;
import com.dispose.repository.ResultRepository;
import com.dispose.repository.SkillRepository;


@RestController
@RequestMapping("/skills")
public class SkillController {
	
	@Value("${app.domain}")
	private String url;
	
	@Value("${app.s3bucket}")
	private String s3;
	
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	/************************/
    /********** Aquest endpoint enviarà al client **************/
    /********** el body del model Skill ************/
    /********** segons l'identificador **************/
    /************************/
	
	@GetMapping("/ids/{id}")
	public ResponseEntity<?> readSkill(@PathVariable long id) {
		Optional<Skill> skill = skillRepository.findById(id);
		if(!skill.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest skill no existeix"),HttpStatus.NOT_FOUND);
		return new ResponseEntity<Skill>(skill.get(),HttpStatus.ACCEPTED);
	}
	
	
	/************************/
    /********** Aquest endpoint enviarà al client **************/
    /********** una llista de Skills ************/
    /************************/
    /************************/
	@GetMapping
	public ResponseEntity<?> readAllSkills() {
		
		List<Skill> skills = skillRepository.findAll();
		List<SkillResponse> skillsResponses = new ArrayList<SkillResponse>();
		for (Skill skill : skills) {
			SkillResponse skillResponse = new SkillResponse(skill.getId(),skill.getSkillId(),skill.getSkillName(),skill.getUrlImage(),skill.isHasImage(),skill.getQuestions().size(),skill.getResults().size());
			skillsResponses.add(skillResponse);
		}
		return new ResponseEntity<List<SkillResponse>>(skillsResponses, HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode GET i el token d'autentificació ************/
    /********** i en rebrà les preguntes de una Skill **************/
    /************************/
	
	@PreAuthorize("hasRole('ROLE_CANDIDATE')")
	@GetMapping("/{skill}/questions")
	public ResponseEntity<?> readQuestionsBySkill(@PathVariable String skill) {
		Optional<Skill> skillObj = skillRepository.findBySkillId(skill);
		if(!skillObj.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta skill no existeix"), HttpStatus.NOT_FOUND);
		Optional<List<Question>> questions = questionRepository.findBySkill(skillObj.get());
		if(!questions.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "No hi ha preguntes en aquesta skill"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<Question>>(questions.get(), HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode GET i el token d'autentificació ************/
    /********** i en rebrà les pràctiques d'una skill **************/
    /************************/
	
	@PreAuthorize("hasRole('ROLE_BUSINESS')")
	@GetMapping("/{skill}/results")
	public ResponseEntity<?> readResultsBySkill(@PathVariable String skill) {
		Optional<Skill> skillObj = skillRepository.findBySkillId(skill);
		if(!skillObj.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta skill no existeix"), HttpStatus.NOT_FOUND);
		Optional<List<Result>> results = resultRepository.findBySkillOrderByRespostesCorrectesDesc(skillObj.get());
		if(!results.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "No hi ha practiques en aquesta skill"), HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<Result>>(results.get(),HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode POST i el token d'autentificació ************/
    /********** i en crearà una skill **************/
    /************************/
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<?> createSkills(@RequestBody List<SkillRequest> skillRequests) {
		
		for (SkillRequest skillRequest : skillRequests) {
			Skill skill = new Skill(skillRequest,s3+skillRequest.getSkillId()+".svg");
			skillRepository.save(skill);
		}
		return new ResponseEntity<Object>(new ApiResponse(true, "Skills creades"), HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** un identificador i el token d'autentificació ************/
    /********** i en borrarà una skill **************/
    /************************/
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/ids/{id}")
	public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
		Optional<Skill> skill = skillRepository.findById(id);
		if(!skill.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta skill no existeix"), HttpStatus.NOT_FOUND);
		skillRepository.delete(skill.get());
		return new ResponseEntity<Object>(new ApiResponse(true, "Skill borrada"),HttpStatus.ACCEPTED);
	}
	
	

}
