package com.projetpfe.projetpfe.Controllers;


import com.projetpfe.projetpfe.DTO.AuthResponseDto;
import com.projetpfe.projetpfe.DTO.LoginDTO;
import com.projetpfe.projetpfe.DTO.RegisterDTO;
import com.projetpfe.projetpfe.Models.Profil;
import com.projetpfe.projetpfe.Models.UserEntity;
import com.projetpfe.projetpfe.Repository.RoleRepository;
import com.projetpfe.projetpfe.Repository.UserRepository;
import com.projetpfe.projetpfe.Security.CustomUserDetailsService;
import com.projetpfe.projetpfe.Security.JWTGenerator;
import com.projetpfe.projetpfe.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auth/")
public class UserController {
    @Autowired
    UserServiceImpl userServ;

    @GetMapping
    public List<UserEntity> getAllUsers() {

        return userServ.getAllUsers();
    }

    @GetMapping("/{idUser}")
    public UserEntity getUserById(@PathVariable long idUser) {

        return userServ.getUserById(idUser);
    }

    @PostMapping("/CreatedUser")
    public UserEntity CreatedUser(@RequestBody UserEntity userEntity) {
        return userServ.CreatedUser(userEntity);
    }

    @PutMapping("/update/{idUser}")
    public UserEntity updateUser(@PathVariable Long idUser, @RequestBody UserEntity userEntity) {
        return userServ.updateUser(idUser, userEntity);
    }

    @DeleteMapping("delete/{idUser}")
    public String deleteUser(@PathVariable Long idUser) {
        return userServ.deleteUser(idUser);
    }



    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,
                          CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;

        this.userDetailsService = userDetailsService;
    }



    //register
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        // Vérification des champs requis
        if (registerDto.getFirstname() == null || registerDto.getLastname() == null) {
            return new ResponseEntity<>("Firstname and lastname are required fields!", HttpStatus.BAD_REQUEST);
        }

        // Créer un nouvel utilisateur
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setCreatedDate(new Date());
        user.setRole(registerDto.getRole());

        // Créer un nouveau profil
        Profil profil = new Profil();
        profil.setFirstname(registerDto.getFirstname());
        profil.setLastname(registerDto.getLastname());
        profil.setMatiere(registerDto.getMatiere());
        profil.setAge(Integer.parseInt(registerDto.getAge()));
        profil.setPhoneNumber(registerDto.getPhoneNumber());
        profil.setUser(user); // Associer le profil à l'utilisateur

        // Enregistrer le profil dans l'utilisateur
        user.setProfil(profil);

        // Enregistrer l'utilisateur dans la base de données
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

        } catch (AuthenticationException e) {
            // Authentication failed, return an error message
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}


