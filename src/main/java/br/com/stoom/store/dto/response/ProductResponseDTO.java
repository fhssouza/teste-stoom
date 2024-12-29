package br.com.stoom.store.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponseDTO {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private List<String> categories;
    private String brand;
    private int stock;
    private String imageUrl;
    private boolean isActive;
    private final List<PriceDTO> prices;

    public ProductResponseDTO(Long id, String sku, String name, String description, List<String> categories, String brand, List<PriceDTO> prices, int stock, String imageUrl, boolean isActive) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.brand = brand;
        this.prices = prices;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public static class PriceDTO {
        private BigDecimal value;
        private String priceType;


        public PriceDTO(BigDecimal value, String priceType) {
            this.value = value;
            this.priceType = priceType;
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
    }
}