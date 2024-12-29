package br.com.stoom.store.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductCreateDTO {

    @NotBlank(message = "SKU is mandatory")
    @ApiModelProperty(value = "Stock Keeping Unit", example = "SKU12345", required = true)
    private String sku;

    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(value = "Name of the product", example = "Smartphone", required = true)
    private String name;

    @NotBlank(message = "Description is mandatory")
    @ApiModelProperty(value = "Description of the product", example = "Latest model smartphone with advanced features", required = true)
    private String description;

    @NotEmpty(message = "Category IDs are mandatory")
    @ApiModelProperty(value = "List of category IDs", example = "[1, 2, 3]", required = true)
    private List<Long> categoryIds;

    @NotNull(message = "Brand ID is mandatory")
    @ApiModelProperty(value = "ID of the brand", example = "1", required = true)
    private Long brandId;

    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    @ApiModelProperty(value = "Stock quantity", example = "100", required = true)
    private int stock;

    @NotBlank(message = "Image URL is mandatory")
    @ApiModelProperty(value = "URL of the product image", example = "http://example.com/image.jpg", required = true)
    private String imageUrl;

    @ApiModelProperty(value = "Product active status", example = "true", required = true)
    private boolean active = true;

    public ProductCreateDTO(String sku, String name, String description, List<Long> categoryIds, Long brandId, int stock, String imageUrl, boolean active) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.categoryIds = categoryIds;
        this.brandId = brandId;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.active = active;
    }

    public ProductCreateDTO() {

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

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}