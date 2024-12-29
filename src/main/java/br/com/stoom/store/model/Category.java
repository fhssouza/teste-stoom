package br.com.stoom.store.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    private String name;

    private boolean active = true;

    private LocalDate createAt;

    private LocalDate updateAt;

    public Category() {
    }

    public Category(Long id, String name, boolean active, LocalDate createAt, LocalDate updateAt) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    @PrePersist
    public void prePersist(){
        createAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate(){
        updateAt = LocalDate.now();
    }
}
