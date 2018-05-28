package sda.pl.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import sda.pl.Product;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int rate;
    String description;
    @ManyToOne
    @JoinColumn
    Product product;
    @ManyToOne
    @JoinColumn
    User user;
    boolean isActive;

}