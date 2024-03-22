package com.projetpfe.projetpfe.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    @JsonBackReference
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id")
    private Courses cours;

}
