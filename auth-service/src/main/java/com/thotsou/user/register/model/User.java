package com.thotsou.user.register.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
