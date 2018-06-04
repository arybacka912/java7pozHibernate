package sda.pl.domain;

import lombok.*;
import sda.pl.Color;
import sda.pl.Price;
import sda.pl.WarehouseName;
import sda.pl.domain.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDetailSet", "cartDetailSet", "productImage", "productRatingSet", "stockSet"})
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;

    @Embedded
    Price price;
    @Enumerated(EnumType.STRING)
    Color color;


    @OneToOne(mappedBy = "product")
    ProductImage productImage;

    @OneToMany(mappedBy = "product")
    Set<OrderDetail> orderDetailSet;

    @OneToMany(mappedBy = "product")
    Set<CartDetail> cartDetailSet;

    @OneToMany(mappedBy = "product")
    Set<ProductRating> productRatingSet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//mapped = wskazanie właściciela relacji
    Set<Stock> stockSet;


    public void addStock(WarehouseName name, BigDecimal amount) {
        if (stockSet == null) {
            stockSet = new HashSet<>();
        }
        Optional<Stock> stockExist = stockSet.stream().filter(s -> s.getWarehouseName().equals(name)).findFirst();
        if (!stockExist.isPresent()) {
            Stock stock = new Stock();
            stock.setProduct(this);
            stock.setWarehouseName(name);
            stock.setAmount(amount);
            stockSet.add(stock);
        } else{

            stockExist.ifPresent(s->{
                s.setAmount(s.getAmount().add(amount));
            });

        }
    }
    public long getSumStockForSale() {
        return getStockSet().stream().filter(stock -> !stock.getWarehouseName().equals(WarehouseName.COMPLAINT))
                .mapToLong(s -> s.getAmount().longValue()).sum();
    }

    public void addProductRating(ProductRating productRating) {
        if (productRating == null) {
            productRatingSet = new HashSet<>();
        }
        productRating.setProduct(this);
        productRatingSet.add(productRating);
    }
}
