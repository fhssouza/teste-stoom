package br.com.stoom.store;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.dto.request.CategoryCreateDTO;
import br.com.stoom.store.dto.response.CategoryResponseDTO;
import br.com.stoom.store.exception.HandlerDataIntegrityViolationException;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryBOTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryBO categoryBO;

    private Category category;
    private CategoryCreateDTO categoryCreateDTO;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setActive(true);

        categoryCreateDTO = new CategoryCreateDTO();
        categoryCreateDTO.setName("Test Category");
    }

    @Test
    public void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<CategoryResponseDTO> categories = categoryBO.findAll();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Test Category", categories.get(0).getName());
    }

    @Test
    public void testFindById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponseDTO categoryResponseDTO = categoryBO.findById(1L);

        assertNotNull(categoryResponseDTO);
        assertEquals("Test Category", categoryResponseDTO.getName());
    }

    @Test
    public void testFindByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HandlerNotFoundException.class, () -> categoryBO.findById(1L));
    }

    @Test
    public void testCreate() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO categoryResponseDTO = categoryBO.create(categoryCreateDTO);

        assertNotNull(categoryResponseDTO);
        assertEquals("Test Category", categoryResponseDTO.getName());
    }

    @Test
    public void testUpdate() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO categoryResponseDTO = categoryBO.update(1L, categoryCreateDTO);

        assertNotNull(categoryResponseDTO);
        assertEquals("Test Category", categoryResponseDTO.getName());
    }

    @Test
    public void testUpdateNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HandlerNotFoundException.class, () -> categoryBO.update(1L, categoryCreateDTO));
    }

    @Test
    public void testActivate() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO categoryResponseDTO = categoryBO.activate(1L);

        assertNotNull(categoryResponseDTO);
        assertTrue(categoryResponseDTO.isActive());
    }

    @Test
    public void testDeactivate() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO categoryResponseDTO = categoryBO.deactivate(1L);

        assertNotNull(categoryResponseDTO);
        assertFalse(categoryResponseDTO.isActive());
    }

    @Test
    public void testDelete() {
        doNothing().when(categoryRepository).deleteById(1L);

        assertDoesNotThrow(() -> categoryBO.delete(1L));
    }

    @Test
    public void testDeleteNotFound() {
        doThrow(EmptyResultDataAccessException.class).when(categoryRepository).deleteById(1L);

        assertThrows(HandlerNotFoundException.class, () -> categoryBO.delete(1L));
    }

    @Test
    public void testDeleteInUse() {
        doThrow(DataIntegrityViolationException.class).when(categoryRepository).deleteById(1L);

        assertThrows(HandlerDataIntegrityViolationException.class, () -> categoryBO.delete(1L));
    }
}
