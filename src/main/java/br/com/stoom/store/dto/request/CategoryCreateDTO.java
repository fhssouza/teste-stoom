package br.com.stoom.store.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class CategoryCreateDTO {

    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(value = "Name of the category", example = "Eletrônicos", required = true)
    private String name;

    public CategoryCreateDTO() {
    }

    public CategoryCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}