package com.codegym.controllers;

import com.codegym.models.Category;
import com.codegym.models.Order;
import com.codegym.models.user.ERole;
import com.codegym.models.user.Role;
import com.codegym.models.user.User;
import com.codegym.payload.request.LoginRequest;
import com.codegym.payload.request.SignupRequest;
import com.codegym.payload.response.JwtResponse;
import com.codegym.payload.response.MessageResponse;
import com.codegym.repositories.user.RoleRepository;
import com.codegym.repositories.user.UserRepository;
import com.codegym.security.jwt.JwtUtils;
import com.codegym.services.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 userDetails.getPhone(),
												 userDetails.getAddress(),
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getAddress(),
							 signUpRequest.getPhone());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("")
	public ResponseEntity<List<User>> listAllUser() {
		List<User> users = (List<User>) userRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<User>> getUser(@PathVariable("id") long id){
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()){
			return new ResponseEntity<Optional<User>>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
		userRepository.save(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Optional<User>> updateUser(@PathVariable("id") long id, @RequestBody User user) {

		Optional<User> currentUser = userRepository.findById(id);

		if (!currentUser.isPresent()) {
			return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
		}

		currentUser.get().setAddress(user.getAddress());
		currentUser.get().setEmail(user.getEmail());
		currentUser.get().setId(user.getId());
		currentUser.get().setMethod(user.getMethod());
		currentUser.get().setName(user.getName());
		currentUser.get().setPassword(user.getPassword());
		currentUser.get().setPhone(user.getPhone());
		currentUser.get().setTotal(user.getTotal());
		currentUser.get().setRoles(user.getRoles());
		currentUser.get().setUsername(user.getUsername());
		return new ResponseEntity<Optional<User>>(currentUser, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		userRepository.deleteById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
}
