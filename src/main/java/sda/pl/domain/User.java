package sda.pl.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"productRatingSet", "cartSet", "orderSet"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    @Column(unique = true)
    String email;
    String zipCode;
    String cityName;
    String street;
    String password;
    @OneToMany(mappedBy = "user")
    Set<Order> orderSet;
    @OneToMany(mappedBy = "user")
    Set<Cart> cartSet;
    @OneToMany(mappedBy = "user")
    Set<ProductRating> productRatingSet;

}