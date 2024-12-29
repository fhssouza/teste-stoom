package br.com.stoom.store.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceResponseDTO {

    private Long id;
    private BigDecimal value;
    private String priceType;
    private LocalDate startDate;
    private LocalDate endDate;

    public PriceResponseDTO() {
    }

    public PriceResponseDTO(Long id, BigDecimal value, String priceType, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.value = value;
        this.priceType = priceType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
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
