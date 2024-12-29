package br.com.stoom.store;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.dto.request.BrandCreateDTO;
import br.com.stoom.store.dto.response.BrandResponseDTO;
import br.com.stoom.store.exception.HandlerDataIntegrityViolationException;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
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
public class BrandBOTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandBO brandBO;

    private Brand brand;
    private BrandCreateDTO brandCreateDTO;

    @BeforeEach
    public void setUp() {
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Test Brand");
        brand.setActive(true);

        brandCreateDTO = new BrandCreateDTO();
        brandCreateDTO.setName("Test Brand");
    }

    @Test
    public void testFindAll() {
        when(brandRepository.findAll()).thenReturn(Arrays.asList(brand));

        List<BrandResponseDTO> brands = brandBO.findAll();

        assertNotNull(brands);
        assertEquals(1, brands.size());
        assertEquals("Test Brand", brands.get(0).getName());
    }

    @Test
    public void testFindById() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

        BrandResponseDTO brandResponseDTO = brandBO.findById(1L);

        assertNotNull(brandResponseDTO);
        assertEquals("Test Brand", brandResponseDTO.getName());
    }

    @Test
    public void testFindByIdNotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HandlerNotFoundException.class, () -> brandBO.findById(1L));
    }

    @Test
    public void testCreate() {
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandResponseDTO brandResponseDTO = brandBO.create(brandCreateDTO);

        assertNotNull(brandResponseDTO);
        assertEquals("Test Brand", brandResponseDTO.getName());
    }

    @Test
    public void testUpdate() {
        when(brandRepository.existsById(1L)).thenReturn(true);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandResponseDTO brandResponseDTO = brandBO.update(1L, brandCreateDTO);

        assertNotNull(brandResponseDTO);
        assertEquals("Test Brand", brandResponseDTO.getName());
    }

    @Test
    public void testUpdateNotFound() {
        when(brandRepository.existsById(1L)).thenReturn(false);

        assertThrows(HandlerNotFoundException.class, () -> brandBO.update(1L, brandCreateDTO));
    }

    @Test
    public void testActivate() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandResponseDTO brandResponseDTO = brandBO.activate(1L);

        assertNotNull(brandResponseDTO);
        assertTrue(brandResponseDTO.isActive());
    }

    @Test
    public void testDeactivate() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandResponseDTO brandResponseDTO = brandBO.deactivate(1L);

        assertNotNull(brandResponseDTO);
        assertFalse(brandResponseDTO.isActive());
    }

    @Test
    public void testDelete() {
        doNothing().when(brandRepository).deleteById(1L);

        assertDoesNotThrow(() -> brandBO.delete(1L));
    }

    @Test
    public void testDeleteNotFound() {
        doThrow(EmptyResultDataAccessException.class).when(brandRepository).deleteById(1L);

        assertThrows(HandlerNotFoundException.class, () -> brandBO.delete(1L));
    }

    @Test
    public void testDeleteInUse() {
        doThrow(DataIntegrityViolationException.class).when(brandRepository).deleteById(1L);

        assertThrows(HandlerDataIntegrityViolationException.class, () -> brandBO.delete(1L));
    }
}