package br.com.stoom.store.dto.request;

public class CategoryRequestDTO {

    private String name;
    private boolean active = true;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
