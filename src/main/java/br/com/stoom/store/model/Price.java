package br.com.stoom.store.model;

import br.com.stoom.store.model.enums.PriceType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_sequence")
    @SequenceGenerator(name = "price_sequence", sequenceName = "PRICE_SEQ", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    private LocalDate startDate;

    private LocalDate endDate;

    public Price() {
    }

    public Price(Long id, Product product, BigDecimal value, PriceType priceType, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.product = product;
        this.value = value;
        this.priceType = priceType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Price(BigDecimal value, PriceType priceType) {
        this.value = value;
        this.priceType = priceType;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
