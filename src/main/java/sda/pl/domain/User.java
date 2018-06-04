package sda.pl.domain;

import lombok.*;
import org.hibernate.id.BulkInsertionCapableIdentifierGenerator;
import sda.pl.repository.CartRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"productRatingSet", "cart", "orderSet", "advertisingBannerSet"})
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
    @OneToOne(mappedBy = "user")
    Cart cart;
    @OneToMany(mappedBy = "user")
    Set<ProductRating> productRatingSet;

    @Transient
    BigDecimal totalOrderPrice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "advertisement_for_the_user",
            joinColumns = @JoinColumn(name = "advertising_banner_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<AdvertisingBanner>advertisingBannersSet;

    public User(Long id, String email, BigDecimal totalOrderPrice) {

        this.id = id;
        this.email = email;
        this.totalOrderPrice = totalOrderPrice;
    }


    public Cart createCart(){
        Cart cart = new Cart();
        cart.setUser(this);
        return cart;

    }



    public ProductRating rateProduct(int rate, String description, Product product) {
        ProductRating productRating = new ProductRating();
        productRating.setActive(false);
        productRating.setRate(rate);
        productRating.setDescription(description);
        productRating.setProduct(product);
        productRating.setUser(this);
        return  productRating;
    }
}