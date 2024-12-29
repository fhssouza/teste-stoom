package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.request.CategoryCreateDTO;
import br.com.stoom.store.dto.request.CategoryRequestDTO;
import br.com.stoom.store.dto.response.BrandResponseDTO;
import br.com.stoom.store.dto.response.CategoryResponseDTO;

import java.util.List;

public interface ICategoryBO {

    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO create(CategoryCreateDTO categoryCreateDTO);
    CategoryResponseDTO update(Long id, CategoryCreateDTO categoryCreateDTO);
    CategoryResponseDTO activate(Long id);
    CategoryResponseDTO deactivate(Long id);
    void delete(Long id);
}
