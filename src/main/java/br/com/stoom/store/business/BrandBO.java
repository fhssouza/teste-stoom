package br.com.stoom.store.business;


import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.dto.request.BrandCreateDTO;
import br.com.stoom.store.dto.response.BrandResponseDTO;
import br.com.stoom.store.exception.HandlerDataIntegrityViolationException;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandBO implements IBrandBO {

    public static final String MSG_BRAND_NOT_FOUND = "Não existe um cadastro de MARCA com código %d";
    public static final String MSG_BRAND_IN_USE = "MARCA de código %d não pode ser removida, pois está em uso";

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandResponseDTO> findAll() {
        return brandRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponseDTO findById(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.map(this::convertToResponseDTO)
                .orElseThrow(() -> new HandlerNotFoundException(
                        String.format(MSG_BRAND_NOT_FOUND, id)));
    }

    @Override
    public BrandResponseDTO create(BrandCreateDTO brandCreateDTO) {
        Brand brand = new Brand();
        brand.setName(brandCreateDTO.getName());
        Brand savedBrand = brandRepository.save(brand);
        return convertToResponseDTO(savedBrand);
    }

    @Override
    public BrandResponseDTO update(Long id, BrandCreateDTO brandCreateDTO) {
        if (brandRepository.existsById(id)) {
            Brand brand = new Brand();
            brand.setId(id);
            brand.setName(brandCreateDTO.getName());
            Brand updatedBrand = brandRepository.save(brand);
            return convertToResponseDTO(updatedBrand);
        } else{
            throw new HandlerNotFoundException(String.format(MSG_BRAND_NOT_FOUND, id));
        }
    }

    @Override
    public BrandResponseDTO activate(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(String.format(MSG_BRAND_NOT_FOUND, id)));
        brand.setActive(true);
        Brand updatedBrand = brandRepository.save(brand);
        return convertToResponseDTO(updatedBrand);
    }

    @Override
    public BrandResponseDTO deactivate(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new HandlerNotFoundException(String.format(MSG_BRAND_NOT_FOUND, id)));
        brand.setActive(false);
        Brand updatedBrand = brandRepository.save(brand);
        return convertToResponseDTO(updatedBrand);
    }

    @Override
    public void delete(Long id) {
        try {
            brandRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new HandlerNotFoundException(
                    (String.format(MSG_BRAND_NOT_FOUND, id)));
        } catch (DataIntegrityViolationException e){
            throw new HandlerDataIntegrityViolationException(
                    String.format(MSG_BRAND_IN_USE, id));
        }
    }

    private BrandResponseDTO convertToResponseDTO(Brand brand) {
        BrandResponseDTO brandResponseDTO = new BrandResponseDTO();
        brandResponseDTO.setId(brand.getId());
        brandResponseDTO.setName(brand.getName());
        brandResponseDTO.setActive(brand.isActive());
        return brandResponseDTO;
    }
}
