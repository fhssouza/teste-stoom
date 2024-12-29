package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.dto.request.CategoryCreateDTO;
import br.com.stoom.store.dto.response.CategoryResponseDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Api(value = "Category Management", tags = "Category Management")
public class CategoryController {

    @Autowired
    private CategoryBO categoryService;

    @GetMapping
    @ApiOperation(value = "Get all categories", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "No categories found")
    })
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        List<CategoryResponseDTO> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get category by ID", response = CategoryResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved category"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<CategoryResponseDTO> findById(
            @ApiParam(value = "ID of the category to retrieve", required = true) @PathVariable Long id) {
        CategoryResponseDTO category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new category", response = CategoryResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created category"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<CategoryResponseDTO> create(
            @ApiParam(value = "Category data to create", required = true) @RequestBody @Valid CategoryCreateDTO categoryCreateDTO, UriComponentsBuilder uriBuilder) {
        CategoryResponseDTO newCategory = categoryService.create(categoryCreateDTO);
        return ResponseEntity
                .created(uriBuilder
                        .path("/api/categories/{id}")
                        .buildAndExpand(newCategory.getId())
                        .toUri())
                .body(newCategory);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing category", response = CategoryResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated category"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<CategoryResponseDTO> update(
            @ApiParam(value = "ID of the category to update", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated category data", required = true) @RequestBody CategoryCreateDTO categoryCreateDTO, UriComponentsBuilder uriBuilder) {
        CategoryResponseDTO updatedCategory = categoryService.update(id, categoryCreateDTO);
        return ResponseEntity
                .created(uriBuilder
                        .path("/api/categories/{id}")
                        .buildAndExpand(updatedCategory.getId())
                        .toUri())
                .body(updatedCategory);
    }

    @PatchMapping("/{id}/activate")
    @ApiOperation(value = "Activate a category", response = CategoryResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully activated category"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<CategoryResponseDTO> activate(
            @ApiParam(value = "ID of the category to activate", required = true) @PathVariable Long id) {
        CategoryResponseDTO activatedCategory = categoryService.activate(id);
        return new ResponseEntity<>(activatedCategory, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deactivate")
    @ApiOperation(value = "Deactivate a category", response = CategoryResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deactivated category"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<CategoryResponseDTO> deactivate(
            @ApiParam(value = "ID of the category to deactivate", required = true) @PathVariable Long id) {
        CategoryResponseDTO deactivatedCategory = categoryService.deactivate(id);
        return new ResponseEntity<>(deactivatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted category"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<Void> delete(
            @ApiParam(value = "ID of the category to delete", required = true) @PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}