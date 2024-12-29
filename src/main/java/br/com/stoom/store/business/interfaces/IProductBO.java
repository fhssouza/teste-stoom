package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.request.ProductCreateDTO;
import br.com.stoom.store.dto.response.ProductResponseDTO;
import br.com.stoom.store.model.Product;

import java.util.List;

public interface IProductBO {

    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    List<ProductResponseDTO> findByBrandName(String brandName);
    List<ProductResponseDTO> findByCategoryName(String categoryName);
    ProductResponseDTO create(ProductCreateDTO productCreateDTO);
    ProductResponseDTO update(Long id, ProductCreateDTO productCreateDTO);
    ProductResponseDTO activate(Long id);
    ProductResponseDTO deactivate(Long id);
    void deleteProduct(Long id);
}
