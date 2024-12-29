package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.dto.request.BrandCreateDTO;
import br.com.stoom.store.dto.response.BrandResponseDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/brands")
@Api(value = "Brand Management", tags = "Brand Management")
public class BrandController {

    @Autowired
    private BrandBO brandService;

    @GetMapping("/")
    @ApiOperation(value = "Get all brands", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
    })
    public ResponseEntity<List<BrandResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get brand by ID", response = BrandResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved brand"),
            @ApiResponse(code = 404, message = "Brand not found")
    })
    public ResponseEntity<BrandResponseDTO> findById(
            @ApiParam(value = "ID of the brand to retrieve", required = true) @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.findById(id));
    }

    @PostMapping("/")
    @ApiOperation(value = "Create a new brand", response = BrandResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created brand"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<BrandResponseDTO> create(
            @ApiParam(value = "Brand data to create", required = true) @RequestBody @Valid BrandCreateDTO brandCreateDTO, UriComponentsBuilder uriBuilder) {
        BrandResponseDTO newBrand = brandService.create(brandCreateDTO);
        return ResponseEntity
                .created(uriBuilder
                        .path("/api/brands/{id}")
                        .buildAndExpand(newBrand.getId())
                        .toUri())
                .body(newBrand);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing brand", response = BrandResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated brand"),
            @ApiResponse(code = 404, message = "Brand not found")
    })
    public ResponseEntity<BrandResponseDTO> update(
            @ApiParam(value = "ID of the brand to update", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated brand data", required = true) @RequestBody @Valid BrandCreateDTO brandCreateDTO, UriComponentsBuilder uriBuilder) {
        BrandResponseDTO updatedBrand = brandService.update(id, brandCreateDTO);

        return ResponseEntity
                .created(uriBuilder
                        .path("/api/brands/{id}")
                        .buildAndExpand(updatedBrand.getId())
                        .toUri())
                .body(updatedBrand);
    }

    @PatchMapping("/{id}/activate")
    @ApiOperation(value = "Activate a brand", response = BrandResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully activated brand"),
            @ApiResponse(code = 404, message = "Brand not found")
    })
    public ResponseEntity<BrandResponseDTO> activate(
            @ApiParam(value = "ID of the brand to activate", required = true) @PathVariable Long id) {
        BrandResponseDTO activatedBrand = brandService.activate(id);
        return new ResponseEntity<>(activatedBrand, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deactivate")
    @ApiOperation(value = "Deactivate a brand", response = BrandResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deactivated brand"),
            @ApiResponse(code = 404, message = "Brand not found")
    })
    public ResponseEntity<BrandResponseDTO> deactivate(
            @ApiParam(value = "ID of the brand to deactivate", required = true) @PathVariable Long id) {
        BrandResponseDTO deactivatedBrand = brandService.deactivate(id);
        return new ResponseEntity<>(deactivatedBrand, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a brand")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted brand"),
            @ApiResponse(code = 404, message = "Brand not found")
    })
    public ResponseEntity<Void> delete(
            @ApiParam(value = "ID of the brand to delete", required = true) @PathVariable Long id) {
        brandService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}