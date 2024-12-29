package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.dto.request.ProductCreateDTO;
import br.com.stoom.store.dto.response.ProductResponseDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api(value = "Product Management", tags = "Product Management")
public class ProductController {

    @Autowired
    private ProductBO productService;

    @GetMapping("/")
    @ApiOperation(value = "Get all products", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Brand or category is inactive")
    })
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by ID", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved product"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 400, message = "Brand or category is inactive")
    })
    public ResponseEntity<ProductResponseDTO> findById(@ApiParam(value = "ID of the product to retrieve", required = true) @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @GetMapping("/brand/name/{brandName}")
    @ApiOperation(value = "Get products by brand name", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved products"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "No products found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<ProductResponseDTO>> getProductsByBrandName(@ApiParam(value = "Brand name to filter products", required = true) @PathVariable String brandName) {
        List<ProductResponseDTO> products = productService.findByBrandName(brandName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/category/name/{categoryName}")
    @ApiOperation(value = "Get products by category name", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved products"),
            @ApiResponse(code = 400, message = "Invalid category name"),
            @ApiResponse(code = 404, message = "No products found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> getProductsByCategoryName(@ApiParam(value = "Category name to filter products", required = true) @PathVariable String categoryName) {
        List<ProductResponseDTO> products = productService.findByCategoryName(categoryName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value = "Create a new product", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created product"),
            @ApiResponse(code = 400, message = "Invalid input or brand/category is inactive")
    })
    public ResponseEntity<ProductResponseDTO> create(@ApiParam(value = "Product data to create", required = true) @RequestBody ProductCreateDTO productCreateDTO, UriComponentsBuilder uriBuilder) {
        ProductResponseDTO newProduct = productService.create(productCreateDTO);
        return ResponseEntity
                .created(uriBuilder
                        .path("/api/products/{id}")
                        .buildAndExpand(newProduct.getId())
                        .toUri())
                .body(newProduct);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing product", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated product"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity<ProductResponseDTO> update(@ApiParam(value = "ID of the product to update", required = true)
                                                                @PathVariable Long id, @ApiParam(value = "Updated product data", required = true)
    @RequestBody ProductCreateDTO productCreateDTO, UriComponentsBuilder uriBuilder) {
            ProductResponseDTO updateProduct = productService.update(id, productCreateDTO);
        return ResponseEntity
                .created(uriBuilder
                        .path("/api/products/{id}")
                        .buildAndExpand(updateProduct.getId())
                        .toUri())
                .body(updateProduct);
    }

    @PatchMapping("/{id}/activate")
    @ApiOperation(value = "Activate a product", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully activated product"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity<ProductResponseDTO> activate(@ApiParam(value = "ID of the product to activate", required = true) @PathVariable Long id) {
        ProductResponseDTO product = productService.activate(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deactivate")
    @ApiOperation(value = "Deactivate a product", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deactivated product"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity<ProductResponseDTO> deactivate(@ApiParam(value = "ID of the product to deactivate", required = true) @PathVariable Long id) {
        ProductResponseDTO product = productService.deactivate(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted product"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity<Void> delete(@ApiParam(value = "ID of the product to delete", required = true) @PathVariable Long id) {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}