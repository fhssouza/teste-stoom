package br.com.stoom.store.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class BrandCreateDTO {

    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(value = "Name of the brand", example = "Nike", required = true)
    private String name;

    public BrandCreateDTO() {
    }

    public BrandCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}