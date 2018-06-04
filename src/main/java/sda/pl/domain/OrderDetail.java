package sda.pl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.pl.Price;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn
    Product product;
    @ManyToOne
    @JoinColumn
    Order order;
    Long amount;
    Price price;

    public OrderDetail(CartDetail cartDetail) {
        this.price = cartDetail.getPrice();
        this.amount = cartDetail.getAmount();
        this.product = cartDetail.getProduct();

    }

}
