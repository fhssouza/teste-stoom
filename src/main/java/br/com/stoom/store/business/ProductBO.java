package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.request.ProductCreateDTO;
import br.com.stoom.store.dto.response.ProductResponseDTO;
import br.com.stoom.store.exception.HandlerDataIntegrityViolationException;
import br.com.stoom.store.exception.HandlerIllegalArgumentException;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductBO implements IProductBO {

    public static final String MSG_PRODUCT_NOT_FOUND = "Não existe um cadastro de PRODUTO com código %d";
    public static final String MSG_PRODUCT_IN_USE = "PRODUTO de código %d não pode ser removida, pois está em uso";
    public static final String MSG_PRODUCT_INACTIVE = "PRODUTO de código %d está inativo";
    public static final String MSG_BRAND_NOT_BLANK = "A descrição da MARCA %s está vazia";
    public static final String MSG_BRAND_NOT_FOUND = "Não existe um cadastro de MARCA com o código %s";
    public static final String MSG_BRAND_INACTIVE = "A MARCA de código %d está inativa";
    public static final String MSG_CATEGORY_NOT_BLANK = "A descrição da CATEGORIA %s está vazia";
    public static final String MSG_CATEGORY_NOT_FOUND = "Não existe cadastro da CATEGORIA informada";
    public static final String MSG_CATEGORY_INACTIVE = "A CATEGORIA de código %d está inativa";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .filter(Product::isActive)
                .map(this::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO findById(Long id) {
    Product product = productRepository.findById(id)
            .orElseThrow(() -> new HandlerNotFoundException(
                    String.format(MSG_PRODUCT_NOT_FOUND, id)));

    if (!product.isActive()) {
        throw new HandlerIllegalArgumentException(String.format(MSG_PRODUCT_INACTIVE, id));
    }

    return mapToProductResponseDTO(product);
}

    @Override
    public List<ProductResponseDTO> findByBrandName(String brandName) {
        if (brandName == null || brandName.trim().isEmpty()) {
            throw new HandlerIllegalArgumentException(String.format(MSG_BRAND_NOT_BLANK, brandName));
        }

        List<Product> products = productRepository.findByBrandNameContainingIgnoreCase(brandName);

        if (products.isEmpty()) {
            throw new HandlerNotFoundException(String.format(MSG_BRAND_NOT_FOUND, brandName));
        }

        return products.stream()
                .map(this::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByCategoryName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new HandlerIllegalArgumentException(String.format(MSG_CATEGORY_NOT_BLANK, categoryName));
        }

        List<Product> products = productRepository.findByCategoryNameContainingIgnoreCase(categoryName);

        if (products.isEmpty()) {
            throw new HandlerNotFoundException(String.format(MSG_CATEGORY_NOT_FOUND, categoryName));
        }

        return products.stream()
                .map(this::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDTO create(ProductCreateDTO dto) {

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_BRAND_NOT_FOUND, dto.getBrandId())));
        if (!brand.isActive()) {
            throw new HandlerIllegalArgumentException(String.format(MSG_BRAND_INACTIVE, dto.getBrandId()));
        }

        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        if (categories.size() != dto.getCategoryIds().size()) {
            throw new HandlerNotFoundException(MSG_CATEGORY_NOT_FOUND);
        }
        for (Category category : categories) {
            if (!category.isActive()) {
                throw new HandlerIllegalArgumentException(String.format(MSG_CATEGORY_INACTIVE, category.getId()));
            }
        }

        Product product = new Product();
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategories(categoryRepository.findAllById((dto.getCategoryIds())));
        product.setBrand(brand);
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);

        return mapToProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductCreateDTO productCreateDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_PRODUCT_NOT_FOUND, id)));

        product.setSku(productCreateDTO.getSku());
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setStock(productCreateDTO.getStock());
        product.setImageUrl(productCreateDTO.getImageUrl());
        product.setActive(productCreateDTO.isActive());
        product = productRepository.save(product);

        return mapToProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO activate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_PRODUCT_NOT_FOUND, id)));
        product.setActive(true);
        product = productRepository.save(product);
        return mapToProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO deactivate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_PRODUCT_NOT_FOUND, id)));
        product.setActive(false);
        product = productRepository.save(product);
        return mapToProductResponseDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        try{
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new HandlerNotFoundException(
                    String.format(MSG_PRODUCT_NOT_FOUND, id));
        }catch (DataIntegrityViolationException e){
            throw new HandlerDataIntegrityViolationException(
                    String.format(MSG_PRODUCT_IN_USE, id));
        }
    }

    private ProductResponseDTO mapToProductResponseDTO(Product product) {
        List<String> categoryNames = product.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        List<ProductResponseDTO.PriceDTO> priceDTOs = product.getPrices().stream()
                .map(price -> new ProductResponseDTO.PriceDTO(
                        price.getValue(),
                        price.getPriceType().toString()))
                .collect(Collectors.toList());

        return new ProductResponseDTO(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                categoryNames,
                product.getBrand().getName(),
                priceDTOs,
                product.getStock(),
                product.getImageUrl(),
                product.isActive()
        );
    }
}