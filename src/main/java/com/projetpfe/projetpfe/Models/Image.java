package com.projetpfe.projetpfe.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Table(name = "Image")
@Entity
public class Image implements Serializable  {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)

    Long idImg ;
    String namePict;
    String type;
    @Lob
    @Column(name = "picByte", columnDefinition  = "LONGBLOB")
    byte[] picByte;

    @OneToOne
    @JoinTable(name ="UserImage")
    @JsonBackReference // Désactive la sérialisation JSON pour cette référence
    private UserEntity user;

    @OneToOne
    @JoinTable(name ="CousesImage")
    @JsonBackReference // Désactive la sérialisation JSON pour cette référence
    private Courses cours;

}
