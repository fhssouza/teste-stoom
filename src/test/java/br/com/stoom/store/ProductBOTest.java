package br.com.stoom.store;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.dto.request.ProductCreateDTO;
import br.com.stoom.store.dto.response.ProductResponseDTO;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductBOTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductBO productBO;

    private ProductCreateDTO productCreateDTO;
    private Product product;
    private Brand brand;
    private Category category;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Brand A");
        brand.setActive(true);

        category = new Category();
        category.setId(1L);
        category.setName("Category A");
        category.setActive(true);

        productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setSku("SKU123");
        productCreateDTO.setName("Product A");
        productCreateDTO.setDescription("Description of Product A");
        productCreateDTO.setBrandId(1L);
        productCreateDTO.setCategoryIds(List.of(1L));
        productCreateDTO.setStock(100);
        productCreateDTO.setImageUrl("http://image.url");

        product = new Product();
        product.setId(1L);
        product.setSku("SKU123");
        product.setName("Product A");
        product.setDescription("Description of Product A");
        product.setBrand(brand);
        product.setCategories(List.of(category));
        product.setStock(100);
        product.setImageUrl("http://image.url");
        product.setActive(true);
    }

    @Test
    void testCreateProductSuccess() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findAllById(productCreateDTO.getCategoryIds())).thenReturn(List.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO response = productBO.create(productCreateDTO);

        assertNotNull(response);
        assertEquals("SKU123", response.getSku());
        assertEquals("Product A", response.getName());
        assertEquals("Brand A", response.getBrand());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProductBrandNotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        HandlerNotFoundException thrown = assertThrows(HandlerNotFoundException.class, () -> {
            productBO.create(productCreateDTO);
        });

        assertEquals("Não existe um cadastro de MARCA com o código 1", thrown.getMessage());
    }

    @Test
    void testCreateProductCategoryNotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findAllById(productCreateDTO.getCategoryIds())).thenReturn(List.of());

        HandlerNotFoundException thrown = assertThrows(HandlerNotFoundException.class, () -> {
            productBO.create(productCreateDTO);
        });

        assertEquals("Não existe cadastro da CATEGORIA informada", thrown.getMessage());
    }

    @Test
    void testFindByIdProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        HandlerNotFoundException thrown = assertThrows(HandlerNotFoundException.class, () -> {
            productBO.findById(1L);
        });

        assertEquals("Não existe um cadastro de PRODUTO com código 1", thrown.getMessage());
    }

    @Test
    void testFindByBrandNameNotFound() {
        when(productRepository.findByBrandNameContainingIgnoreCase("Brand A")).thenReturn(List.of());

        HandlerNotFoundException thrown = assertThrows(HandlerNotFoundException.class, () -> {
            productBO.findByBrandName("Brand A");
        });

        assertEquals("Não existe um cadastro de MARCA com o código Brand A", thrown.getMessage());
    }

    @Test
    void testFindByCategoryNameNotFound() {
        when(productRepository.findByCategoryNameContainingIgnoreCase("Category A")).thenReturn(List.of());

        HandlerNotFoundException thrown = assertThrows(HandlerNotFoundException.class, () -> {
            productBO.findByCategoryName("Category A");
        });

        assertEquals("Não existe cadastro da CATEGORIA informada", thrown.getMessage());
    }
}
