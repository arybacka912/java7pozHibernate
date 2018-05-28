package sda.pl.domain;

import lombok.*;
import sda.pl.Price;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "orderDetailSet")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime date;
    Price totalPrice;
    @ManyToOne
    @JoinColumn
    User user;

    @Column(name = "adres_wysylki")
    String cityName;
    boolean RODO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    Set<OrderDetail> orderDetailSet;


    public void addOrderDetail(OrderDetail od){
        if(orderDetailSet == null){
            orderDetailSet = new HashSet<>();
        }

        od.setOrder(this);
        orderDetailSet.add(od);

    }

    public void calculateTotalPrice(){
        getTotalPrice().setPriceGross(BigDecimal.ZERO);
        getTotalPrice().setPriceNet(BigDecimal.ZERO);
        getTotalPrice().setPriceSymbol("PLN");

        getOrderDetailSet().forEach(
                cd ->
                {
                    getTotalPrice().setPriceGross(getTotalPrice().getPriceGross()
                            .add(
                                    cd.getPrice().getPriceGross().multiply(new BigDecimal(cd.getAmount()))
                            )
                    );

                    getTotalPrice().setPriceNet(getTotalPrice().getPriceNet()
                            .add(
                                    cd.getPrice().getPriceNet().multiply(new BigDecimal(cd.getAmount()))
                            )
                    );
                }
        );
    }

}