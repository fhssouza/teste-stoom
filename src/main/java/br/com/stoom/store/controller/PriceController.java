package br.com.stoom.store.controller;

import br.com.stoom.store.business.PriceBO;
import br.com.stoom.store.dto.request.PriceCreateDTO;
import br.com.stoom.store.dto.response.PriceResponseDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Api(value = "Price Management", tags = "Price Management")
public class PriceController {

    @Autowired
    private PriceBO priceService;

    @PostMapping("/{productId}/prices")
    @ApiOperation(value = "Add a new price to a product", response = PriceResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added price"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity<PriceResponseDTO> addPriceToProduct(
            @ApiParam(value = "ID of the product to add the price to", required = true) @PathVariable Long productId,
            @ApiParam(value = "Price data to add", required = true) @RequestBody @Valid PriceCreateDTO priceCreateDTO) {

        PriceResponseDTO price = priceService.addPriceToProduct(productId, priceCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(price);
    }
}