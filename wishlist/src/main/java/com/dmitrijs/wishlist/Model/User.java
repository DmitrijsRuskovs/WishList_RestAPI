package com.dmitrijs.wishlist.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User implements Serializable {

        private static final long serialVersionUID = 4048798961366546485L;

        @Id
        @Column(name="id", nullable = false)
        @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        private Long id;

        @Column(name="name", nullable = false)
        private String name;

        @Column(name="type", nullable = false)
        private String type;

        @Column(name="email")
        private String email;
}
