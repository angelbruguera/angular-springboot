package com.dispose.controller;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dispose.model.Answer;
import com.dispose.model.Question;
import com.dispose.model.Result;
import com.dispose.model.Skill;
import com.dispose.model.User;
import com.dispose.payload.AnswerRequest;
import com.dispose.payload.ApiResponse;
import com.dispose.payload.ResultRequest;
import com.dispose.repository.AnswerRepository;
import com.dispose.repository.QuestionRepository;
import com.dispose.repository.ResultRepository;
import com.dispose.repository.SkillRepository;
import com.dispose.repository.UserRepository;
import com.dispose.security.CurrentUser;
import com.dispose.security.UserPrincipal;




@RestController
@RequestMapping("/results")
public class ResultController {
	
	@Value("${app.domain}")
	private String url;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	/************************/
    /********** Aquest endpoint enviarà al client **************/
    /********** el body del model Result ************/
    /********** segons l'identificador **************/
    /************************/
		
	@GetMapping("/ids/{id}")
	public ResponseEntity<?> readResult(@PathVariable Long id) {
		Optional<Result> result = resultRepository.findById(id);
		if(!result.isPresent())return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta practica no existeix"), HttpStatus.NOT_FOUND); 
		return new ResponseEntity<Result>(result.get(),HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode POST i el token d'autentificació ************/
    /********** i en crearà una pràctica **************/
    /************************/
	
	@PreAuthorize("hasRole('ROLE_CANDIDATE')")
	@PostMapping
	public ResponseEntity<?> createResult(@Valid @RequestBody ResultRequest resultRequest,@CurrentUser UserPrincipal principal) {
		Result result;
		Optional<User> user = userRepository.findByUsername(principal.getUsername());
		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"),HttpStatus.NOT_FOUND);
		Optional<Skill> skill = skillRepository.findBySkillId(resultRequest.getSkill());
		if(!skill.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta skill no existeix"), HttpStatus.NOT_FOUND);
		
		Optional<List<Question>> questions = questionRepository.findTop10BySkillOrderByRand(skill.get().getId());
		if(!questions.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "No hi ha preguntes suficients"), HttpStatus.NOT_FOUND);
		Optional<Result> resultRepeated = resultRepository.findBySkillIdAndCandidateId(skill.get().getId(), user.get().getId());
		
		if(resultRepeated.isPresent()) {
			resultRepository.deleteById(resultRepeated.get().getId());
		}
		
		
		result = new Result(user.get(),skill.get(),questions.get(),questions.get().size(),questions.get().size(),0);
		
		
			
			
		resultRepository.save(result);
		
		URI location = ServletUriComponentsBuilder
                .fromHttpUrl(url).path("/results/ids/{id}")
                .buildAndExpand(result.getId()).toUri();
		
		return ResponseEntity.created(location).body(result);
		
		
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el skill com a identificador i el token d'autentificació ************/
    /********** i n'anirà contestant les preguntes **************/
    /************************/
	@PreAuthorize("hasRole('ROLE_CANDIDATE')")
	@PostMapping("/{skillname}")
	public ResponseEntity<?> postAnswer(@PathVariable String skillname,@Valid @RequestBody AnswerRequest answerRequest,@CurrentUser UserPrincipal principal)
	{
		Optional<User> user = userRepository.findByUsername(principal.getUsername());
		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"),HttpStatus.NOT_FOUND);
		Optional<Skill> skill = skillRepository.findBySkillId(skillname);
		if(!skill.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta skill no existeix"), HttpStatus.NOT_FOUND);
		Optional<Result> result = resultRepository.findBySkillIdAndCandidateId(skill.get().getId(),user.get().getId());
		if(!result.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta practica no existeix"), HttpStatus.NOT_FOUND);
		if(result.get().getRespostesContestades()<=0) 
		{ 
			return new ResponseEntity<Object>(new ApiResponse(true, "Questionari finalitzat"), HttpStatus.ACCEPTED);
		}
		Answer answer = new Answer(result.get(),answerRequest.getIdQuestion(), answerRequest.getRespostaSeleccionada());
		result.get().setRespostesContestades(result.get().getRespostesContestades()-1);
		Optional<Question> question = questionRepository.findById(answerRequest.getIdQuestion());
		if(!question.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta pregunta no existeix"), HttpStatus.NOT_FOUND);
		if(answer.getRespostaSeleccionada().equals(question.get().getRespostaCorrecte())) { 
			result.get().setRespostesCorrectes(result.get().getRespostesCorrectes()+1);
			answer.setCorrecte(true);
		}
		answerRepository.save(answer);
		
		return new ResponseEntity<Object>(new ApiResponse(false, "Falten " + result.get().getRespostesContestades()+" preguntes"), HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el identificador del model Result i el token d'autentificació ************/
    /********** i en borrarà la practica **************/
    /************************/
	@PreAuthorize("hasRole('ROLE_CANDIDATE')")
	@DeleteMapping("/ids/{id}")
	public ResponseEntity<?> deleteResult(@PathVariable(value = "id") Long id, @CurrentUser UserPrincipal principal) {
		Optional<User> user = userRepository.findByUsername(principal.getUsername());
		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"),HttpStatus.NOT_FOUND);
		Optional<Result> result = resultRepository.findById(id);
		if(!result.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta practica no existeix"),HttpStatus.NOT_FOUND);
		if(!principal.getUsername().equals(result.get().getCandidateUsername())) return new ResponseEntity<Object>(new ApiResponse(false, "No estas autoritzat per borrar la practica"),HttpStatus.UNAUTHORIZED);
		resultRepository.delete(result.get());
		return new ResponseEntity<Object>(new ApiResponse(true, "Practica borrada"),HttpStatus.ACCEPTED);
	}
	
}
