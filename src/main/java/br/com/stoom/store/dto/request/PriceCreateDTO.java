package br.com.stoom.store.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceCreateDTO {

    @NotNull(message = "Value is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than 0")
    @ApiModelProperty(value = "Price value", example = "99.99", required = true)
    private BigDecimal value;

    @NotBlank(message = "Price type is mandatory")
    @ApiModelProperty(value = "Type of the price", example = "PROMOCAO", required = true)
    private String priceType;

    @NotNull(message = "Start date is mandatory")
    @ApiModelProperty(value = "Start date of the price", example = "2024-01-01", required = true)
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    @ApiModelProperty(value = "End date of the price", example = "2024-12-31", required = true)
    private LocalDate endDate;

    public PriceCreateDTO(BigDecimal value, String priceType, LocalDate startDate, LocalDate endDate) {
        this.value = value;
        this.priceType = priceType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PriceCreateDTO() {

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