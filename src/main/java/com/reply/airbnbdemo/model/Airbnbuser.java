package com.reply.airbnbdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "airbnbuser")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airbnbuser {
    @Id
    @Column(name = "AirBnBUID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DOB")
    private LocalDate dob;

    @Size(max = 20)
    @NotNull
    @Column(name = "Email", nullable = false, length = 20)
    private String email;

    @Size(max = 20)
    @NotNull
    @Column(name = "UserPassword", nullable = false, length = 20)
    private String userPassword;

    @Column(name = "Gender")
    private Character gender;

    @Size(max = 100)
    @Column(name = "About", length = 100)
    private String about;

    @Size(max = 15)
    @NotNull
    @Column(name = "Phone", nullable = false, length = 15)
    private String phone;

    @Size(max = 20)
    @Column(name = "ProfilePhotoName", length = 20)
    private String profilePhotoName;

    @Column(name = "ProfilePhoto")
    private byte[] profilePhoto;

    @Size(max = 100)
    @Column(name = "Address", length = 100)
    private String address;

    @Size(max = 20)
    @NotNull
    @Column(name = "Fname", nullable = false, length = 20)
    private String fname;

    @Size(max = 20)
    @Column(name = "MInitial", length = 20)
    private String mInitial;

    @Size(max = 20)
    @Column(name = "LName", length = 20)
    private String lName;

    @Column(name = "Created")
    private Instant created;

    @Column(name = "LoginCnt")
    private Integer loginCnt;

    @Column(name = "LastLogin")
    private Instant lastLogin;

    @Size(max = 20)
    @Column(name = "EmName", length = 20)
    private String emName;

    @Size(max = 20)
    @Column(name = "EmRelationship", length = 20)
    private String emRelationship;

    @Size(max = 15)
    @Column(name = "EmPreferredLang", length = 15)
    private String emPreferredLang;

    @Size(max = 20)
    @NotNull
    @Column(name = "EmEmail", nullable = false, length = 20)
    private String emEmail;

    @Size(max = 3)
    @NotNull
    @Column(name = "EmCountryCode", nullable = false, length = 3)
    private String emCountryCode;

    @Size(max = 15)
    @NotNull
    @Column(name = "EmPhone", nullable = false, length = 15)
    private String emPhone;

    @OneToOne(mappedBy = "airbnbuser", cascade = CascadeType.ALL)
    private Guest guest;

    @OneToOne(mappedBy = "airbnbuser", cascade = CascadeType.ALL)
    private Host host;

}