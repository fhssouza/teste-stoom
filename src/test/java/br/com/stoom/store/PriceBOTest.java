package br.com.stoom.store;

import br.com.stoom.store.business.PriceBO;
import br.com.stoom.store.dto.request.PriceCreateDTO;
import br.com.stoom.store.dto.response.PriceResponseDTO;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Price;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.model.enums.PriceType;
import br.com.stoom.store.repository.PriceRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceBOTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PriceBO priceBO;

    private Product product;
    private PriceCreateDTO priceCreateDTO;
    private Price price;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        priceCreateDTO = new PriceCreateDTO();
        priceCreateDTO.setValue(new BigDecimal("100.00"));
        priceCreateDTO.setPriceType("PROMOCAO");
        priceCreateDTO.setStartDate(LocalDate.now());
        priceCreateDTO.setEndDate(LocalDate.now().plusDays(10));

        price = new Price();
        price.setId(1L);
        price.setProduct(product);
        price.setValue(priceCreateDTO.getValue());
        price.setPriceType(PriceType.valueOf(priceCreateDTO.getPriceType()));
        price.setStartDate(priceCreateDTO.getStartDate());
        price.setEndDate(priceCreateDTO.getEndDate());
    }

    @Test
    public void testAddPriceToProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(priceRepository.save(any(Price.class))).thenReturn(price);

        PriceResponseDTO priceResponseDTO = priceBO.addPriceToProduct(1L, priceCreateDTO);

        assertNotNull(priceResponseDTO);
        assertEquals(price.getId(), priceResponseDTO.getId());
        assertEquals(price.getValue(), priceResponseDTO.getValue());
        assertEquals(price.getPriceType().toString(), priceResponseDTO.getPriceType());
        assertEquals(price.getStartDate(), priceResponseDTO.getStartDate());
        assertEquals(price.getEndDate(), priceResponseDTO.getEndDate());
    }

    @Test
    public void testAddPriceToProductProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HandlerNotFoundException.class, () -> priceBO.addPriceToProduct(1L, priceCreateDTO));
    }
}
