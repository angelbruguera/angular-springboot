package com.dispose.controller;

import com.dispose.model.Role;
import com.dispose.model.RoleName;
import com.dispose.model.User;
import com.dispose.payload.ApiResponse;

import com.dispose.payload.LoginRequest;
import com.dispose.payload.SignUpRequest;
import com.dispose.payload.UserSummary;
import com.dispose.repository.RoleRepository;
import com.dispose.repository.UserRepository;

import com.dispose.security.JwtTokenProvider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    /************************/
    /********** Aquest endpoint rebra l'estructura d'un LoginRequest com a JSON**************/
    /********** i li retornarà un token amb la informació del usuari *************/
    /************************/

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        Optional<User> user = userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());
        if(!user.isPresent()) return new ResponseEntity<Object>(new ApiResponse(false, "Aquest usuari no existeix"),
                HttpStatus.BAD_REQUEST);
       
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        User userGet =user.get();
        UserSummary userSummary = new UserSummary(userGet.getId(), userGet.getUsername(), userGet.getName(), userGet.getEmail(), userGet.getProfileURL(), tokenProvider.getExpiration(jwt), userGet.getRoleName(), jwt);
        return new ResponseEntity<UserSummary>(userSummary,HttpStatus.ACCEPTED);
    }
    
    /************************/
    /********** Aquest endpoint rebra l'estructura d'un SignUpRequest com a JSON, **************/
    /********** registrarà l'usuari i el client rebrà un possible error o el OK del servidor *************/
    /************************/
    /************************/
    /********** Al SignUpRequest determinarem el rol que volem **************/
    /***********************/
    /************************/

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Aquest username no està disponible!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Aquest email no està disponible!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        Role userRole;
        
        switch (signUpRequest.getRole()) {
		case "business":
			userRole = roleRepository.findByName(RoleName.ROLE_BUSINESS);
			break;
		case "candidate":
			userRole = roleRepository.findByName(RoleName.ROLE_CANDIDATE);
			break;
		case "admin":
			userRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
			break;
		default:
				return new ResponseEntity<Object>(new ApiResponse(false, "Necessitem els rols 'candidate' o 'business'"), HttpStatus.BAD_REQUEST);
		}
        
        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Usuari registrat correctament"));
    }
    
    
    
}
