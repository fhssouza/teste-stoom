package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.request.CategoryCreateDTO;
import br.com.stoom.store.dto.response.CategoryResponseDTO;
import br.com.stoom.store.exception.HandlerDataIntegrityViolationException;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryBO implements ICategoryBO {

    public static final String MSG_CATEGORY_NOT_FOUND = "Não existe um cadastro de CATEGORIA com código %d";
    public static final String MSG_CATEGORY_IN_USE = "CATEGORIA de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_CATEGORY_NOT_FOUND, id)));
        return convertToResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO create(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();
        category.setName(categoryCreateDTO.getName());
        category.setActive(true);
        Category savedCategory = categoryRepository.save(category);
        return convertToResponseDTO(savedCategory);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryCreateDTO categoryCreateDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_CATEGORY_NOT_FOUND, id)));
        category.setName(categoryCreateDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDTO(updatedCategory);
    }

    @Override
    public CategoryResponseDTO activate(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_CATEGORY_NOT_FOUND, id)));
        category.setActive(true);
        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDTO(updatedCategory);
    }

    @Override
    public CategoryResponseDTO deactivate(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_CATEGORY_NOT_FOUND, id)));
        category.setActive(false);
        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDTO(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        try{
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new HandlerNotFoundException(
                    String.format(MSG_CATEGORY_NOT_FOUND, id));
        }catch (DataIntegrityViolationException e){
            throw new HandlerDataIntegrityViolationException(
                    String.format(MSG_CATEGORY_IN_USE, id));
        }
    }

    private CategoryResponseDTO convertToResponseDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setActive(category.isActive());
        return categoryResponseDTO;
    }
}
