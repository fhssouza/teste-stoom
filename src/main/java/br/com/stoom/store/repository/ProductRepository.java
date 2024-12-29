package br.com.stoom.store.repository;

import br.com.stoom.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.brand.name) LIKE LOWER(CONCAT('%', :brandName, '%'))")
    List<Product> findByBrandNameContainingIgnoreCase(String brandName);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
    List<Product> findByCategoryNameContainingIgnoreCase(String categoryName);


}