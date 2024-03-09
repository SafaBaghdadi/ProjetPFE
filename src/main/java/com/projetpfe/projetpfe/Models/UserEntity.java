package com.projetpfe.projetpfe.Models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    private String username;
    private String email;
    private String password ;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private UserRole role ;



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference // Désactive la sérialisation JSON pour cette référence
    private Profil profil;


    //image
    @OneToOne(mappedBy = "user")
    @JsonManagedReference // Désactive la sérialisation JSON pour cette référence
    private Image image;

    //Quiz
    @OneToMany(mappedBy = "Enseignant")
    private List<Quiz> quizzes; // Les quizzes proposés par l'enseignant


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    // Relation avec les cours autorisés à la consultation
    @ManyToMany
    @JoinTable(
            name = "user_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")
    )
    private List<Courses> coursAutorises;
}
