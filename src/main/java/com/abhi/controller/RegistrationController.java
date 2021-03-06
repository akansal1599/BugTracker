package com.abhi.controller;

import com.abhi.model.authentication.AuthenticationRequest;
import com.abhi.model.authentication.AuthenticationResponse;
import com.abhi.model.UserEntity;
import com.abhi.service.SecurityService;
import com.abhi.service.UserDetailsServiceImpl;
import com.abhi.service.UserService;
import com.abhi.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Autowired
    private JwtUtil jwtTokenUtil;
    
    @Autowired
	private AuthenticationManager authenticationManager;

    
    @PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
				);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password",e);
		}
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@ModelAttribute("userForm") UserEntity userEntity, BindingResult bindingResult) {

        // TODO: 11.01.2020 userValidator
    	
    	if (bindingResult.hasErrors()) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    	try {
    		userService.addUser(userEntity);
			return ResponseEntity.status(HttpStatus.CREATED).body("user created");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
        //securityService.autoLogin(userEntity.getEmail(), userEntity.getPasswordConfirm());

    }
    
}
