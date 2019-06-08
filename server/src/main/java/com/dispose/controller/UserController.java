package com.dispose.controller;

import com.dispose.model.Question;
import com.dispose.model.Result;
import com.dispose.model.User;
import com.dispose.payload.*;
import com.dispose.repository.QuestionRepository;
import com.dispose.repository.ResultRepository;
import com.dispose.repository.UserRepository;
import com.dispose.security.UserPrincipal;


import com.dispose.security.CurrentUser;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ResultRepository resultRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    /************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode GET i el token d'autentificació ************/
    /********** i n'obtindrà la informació propia **************/
    /************************/
    
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@CurrentUser UserPrincipal principal) {
    	Optional<User> user = userRepository.findByUsername(principal.getUsername());
    	if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user.get(),HttpStatus.ACCEPTED);
    }
    
    /************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode GET i el token d'autentificació ************/
    /********** i n'obtindrà els resultats propis **************/
    /************************/
    
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    @GetMapping("/me/results")
    public ResponseEntity<?> getResultsUser(@CurrentUser UserPrincipal principal) {
    	Optional<User> user = userRepository.findByUsername(principal.getUsername());
    	if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Result>>(user.get().getResults(),HttpStatus.ACCEPTED);
    }
    
    /************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode GET i el token d'autentificació ************/
    /********** i n'obtindrà les preguntes propies **************/
    /************************/
    
    @PreAuthorize("hasRole('ROLE_BUSINESS')")
    @GetMapping("/me/questions")
    public ResponseEntity<?> getQuestionsUser(@CurrentUser UserPrincipal principal) {
    	Optional<User> user = userRepository.findByUsername(principal.getUsername());
    	if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Question>>(user.get().getQuestions(), HttpStatus.ACCEPTED);
    }
    
    /************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode GET i l'identificador d'un usuari ************/
    /********** i n'obtindrà els seus resultats **************/
    /************************/
    
    @PreAuthorize("hasRole('ROLE_BUSINESS')")
  	@GetMapping(value = "/{username}/results")
  	public ResponseEntity<?> readResultsByUsername(@PathVariable String username) {
  		Optional<User> user = userRepository.findByUsername(username);
  		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"),HttpStatus.NOT_FOUND);
  		Optional<List<Result>> results = resultRepository.findByCandidate(user.get());
  		if(!results.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "No hi ha practiques creades per " + username), HttpStatus.NOT_FOUND);
  		
  		return new ResponseEntity<List<Result>>(results.get(),HttpStatus.ACCEPTED);
  	}
    
    /************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode GET i l'identificador d'un usuari ************/
    /********** i n'obtindrà les seves preguntes **************/
    /************************/
  	
  	@PreAuthorize("hasRole('ROLE_CANDIDATE')")
  	@GetMapping(value = "/{username}/questions")
  	public ResponseEntity<?> readQuestionsByUsername(@PathVariable String username) {
  		
  		Optional<User> user = userRepository.findByUsername(username);
  		if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"), HttpStatus.NOT_FOUND);
  		
  		Optional<List<Question>> questions = questionRepository.findByQuestionUser(user.get());
  		if(!questions.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "No hi preguntes creades per "+username),HttpStatus.NOT_FOUND);
  		
  		return new ResponseEntity<List<Question>>(questions.get(), HttpStatus.ACCEPTED);
  	}
  	
  	/************************/
    /********** Aquest endpoint rebrà del client **************/
    /********** el metode DELETE i el token d'autentificació ************/
    /********** i borrarà l'usuari **************/
    /************************/
    
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteProfile(@CurrentUser UserPrincipal principal) {
        
        Optional<User> user = userRepository.findByUsername(principal.getUsername());
        if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, principal.getUsername() + " no existeix"), HttpStatus.NOT_FOUND);
        
        userRepository.delete(user.get());
        
        return new ResponseEntity<Object>(new ApiResponse(true, principal.getUsername() + " borrat"),HttpStatus.ACCEPTED); 
    }
    
    /************************/
    /********** Aquest metode intercepta les peticions i n'extreu el token **************/
    /************************/
    
    protected String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


}
