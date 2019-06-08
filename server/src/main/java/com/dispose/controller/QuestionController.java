package com.dispose.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dispose.model.Question;
import com.dispose.model.Skill;
import com.dispose.model.User;
import com.dispose.payload.ApiResponse;
import com.dispose.payload.QuestionRequest;
import com.dispose.repository.QuestionRepository;
import com.dispose.repository.SkillRepository;
import com.dispose.repository.UserRepository;
import com.dispose.security.CurrentUser;
import com.dispose.security.UserPrincipal;

@RestController
@RequestMapping("/questions")
public class QuestionController {
	
	@Value("${app.domain}")
	private String url;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private UserRepository userRepository;

	/************************/
    /********** Aquest endpoint enviarà al client **************/
    /********** el body del model Question ************/
    /********** segons l'identificador **************/
    /************************/
	
	@GetMapping("/ids/{id}")
	public ResponseEntity<?> readQuestion(@PathVariable Long id) {
		Optional<Question> question = questionRepository.findById(id);
		if(!question.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta pregunta no existeix"),HttpStatus.NOT_FOUND);
		return new ResponseEntity<Question>(question.get(), HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode POST i el token d'autentificació ************/
    /********** i en crearà la pregunta **************/
    /************************/
	
	@PreAuthorize("hasRole('ROLE_BUSINESS')")
	@PostMapping
	public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequest questionRequest,@CurrentUser UserPrincipal principal) {
		Optional<User> user = userRepository.findByUsername(principal.getUsername());
		
		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"), HttpStatus.NOT_FOUND);
		Optional<Skill> skill = skillRepository.findBySkillId(questionRequest.getSkill());
		if(!skill.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta skill no existeix"), HttpStatus.NOT_FOUND);
		
		Question question = new Question(user.get(),questionRequest.getEnunciat(),questionRequest.getResposta1(),questionRequest.getResposta2(),questionRequest.getResposta3(),questionRequest.getResposta4(),questionRequest.getRespostaCorrecta(),skill.get());
	
		Question questionId = questionRepository.save(question);
		URI location = ServletUriComponentsBuilder
                .fromHttpUrl(url).path("/questions/ids/{id}")
                .buildAndExpand(questionId.getId()).toUri();
		
		return ResponseEntity.created(location).body(questionId);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el identificador del model Question i el token d'autentificació ************/
    /********** i en modificarà la pregunta **************/
    /************************/
	
	@PreAuthorize("hasRole('ROLE_BUSINESS')")
	@PutMapping("/ids/{id}")
	public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question questionRequest,@CurrentUser UserPrincipal principal) {
		
		Optional<User> user = userRepository.findByUsername(principal.getUsername());
		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"), HttpStatus.NOT_FOUND);
		
		Optional<Question> questionWillModify = questionRepository.findById(id);
		if(!questionWillModify.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta pregunta no existeix"), HttpStatus.NOT_FOUND);
		
		if(!user.get().getUsername().equals(questionWillModify.get().getQuestionUsername())) return new ResponseEntity<Object>(new ApiResponse(false, "No estas autoritzat per modificar la pregunta"),HttpStatus.UNAUTHORIZED);
		
		Question question = new Question();
		BeanUtils.copyProperties(questionRequest, question);
		question.setId(questionWillModify.get().getId());
		questionRepository.save(question);
		return new ResponseEntity<Question>(question,HttpStatus.ACCEPTED);
	}
	
	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el identificador del model Question i el token d'autentificació ************/
    /********** i en borrarà la pregunta **************/
    /************************/
	
	@PreAuthorize("hasRole('ROLE_BUSINESS')")
	@DeleteMapping("/ids/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Long id, @CurrentUser UserPrincipal principal) {
		Optional<User> user = userRepository.findByUsername(principal.getUsername());
		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"), HttpStatus.NOT_FOUND);
		
		Optional<Question> question = questionRepository.findById(id);
		if(!question.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquesta pregunta no existeix"), HttpStatus.NOT_FOUND);
		
		if(!user.get().getUsername().equals(question.get().getQuestionUsername())) return new ResponseEntity<Object>(new ApiResponse(false, "No estas autoritzat per borrar la practica"),HttpStatus.UNAUTHORIZED);
		
		questionRepository.delete(question.get());
		return new ResponseEntity<Object>(new ApiResponse(true, "Pregunta borrada"),HttpStatus.ACCEPTED);
	}
	

}