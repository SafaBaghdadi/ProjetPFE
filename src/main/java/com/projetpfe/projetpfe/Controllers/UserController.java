package com.projetpfe.projetpfe.Controllers;

import com.projetpfe.projetpfe.DTO.AuthResponseDto;
import com.projetpfe.projetpfe.DTO.LoginDTO;
import com.projetpfe.projetpfe.DTO.RegisterDTO;
import com.projetpfe.projetpfe.Models.Profil;
import com.projetpfe.projetpfe.Models.UserEntity;
import com.projetpfe.projetpfe.Models.UserRole;
import com.projetpfe.projetpfe.Repository.RoleRepository;
import com.projetpfe.projetpfe.Repository.UserRepository;
import com.projetpfe.projetpfe.Security.CustomUserDetailsService;
import com.projetpfe.projetpfe.Security.JWTGenerator;
import com.projetpfe.projetpfe.Service.UserServiceImpl;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            // Vérification des champs requis
            if (registerDto.getFirstname() == null || registerDto.getLastname() == null) {
                return new ResponseEntity<>("Firstname and lastname are required fields!", HttpStatus.BAD_REQUEST);
            }

            // Vérifier si l'e-mail est null ou vide
            if (!StringUtils.isEmpty(registerDto.getEmail())) {
                // Vérifier si l'e-mail est valide
                if (!registerDto.isValidEmail()) {
                    return new ResponseEntity<>("Invalid email format!", HttpStatus.BAD_REQUEST);
                }
            } else {
                // Gérer le cas où l'e-mail est null ou vide
                return new ResponseEntity<>("Email is required!", HttpStatus.BAD_REQUEST);
            }
            // Vérifier si l'e-mail est déjà utilisé
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                return new ResponseEntity<>("Email already in use!", HttpStatus.BAD_REQUEST);
            }

// Vérifier si l'âge est requis pour un élève et s'il est fourni
            if (registerDto.getRole() == UserRole.ELEVE && (registerDto.getAge() == null || registerDto.getAge().describeConstable().isEmpty())) {
                return new ResponseEntity<>("Age is required for student!", HttpStatus.BAD_REQUEST);
            }
            // Créer un nouvel utilisateur
            UserEntity user = new UserEntity();
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setCreatedDate(new Date());
            user.setRole(registerDto.getRole());

            // Créer un nouveau profil en fonction du rôle de l'utilisateur
            Profil profil = new Profil();
            profil.setFirstname(registerDto.getFirstname());
            profil.setLastname(registerDto.getLastname());

            if (registerDto.getRole() == UserRole.PARENT) {
                profil.setPhoneNumber(registerDto.getPhoneNumber());
            } else if (registerDto.getRole() == UserRole.ENSEIGNANT) {
                profil.setMatiere(registerDto.getMatiere());
                profil.setPhoneNumber(registerDto.getPhoneNumber());
            } else if (registerDto.getRole() == UserRole.ELEVE) {
                profil.setAge(registerDto.getAge());
                }

            profil.setUser(user); // Associer le profil à l'utilisateur

            // Enregistrer le profil dans l'utilisateur
            user.setProfil(profil);

            // Enregistrer l'utilisateur dans la base de données
            userRepository.save(user);

            return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
        } catch (Exception e) {
            // Gérer d'autres exceptions
            return new ResponseEntity<>("An error occurred during registration", HttpStatus.INTERNAL_SERVER_ERROR);
    }}



    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @NotNull LoginDTO loginDto) {
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


