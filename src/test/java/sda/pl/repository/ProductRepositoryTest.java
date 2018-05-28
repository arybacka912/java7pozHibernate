package sda.pl.repository;

import org.junit.Assert;
import org.junit.Test;
import sda.pl.Product;

import java.util.Optional;

import static org.junit.Assert.*;

public class ProductRepositoryTest {

    @Test
    public void findProduct() {
        Optional<Product> product = ProductRepository.findProduct(2L);

        Assert.assertTrue(product.get().getProductImage().getImage() != null);
    }

}