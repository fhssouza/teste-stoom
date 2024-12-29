package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.request.BrandCreateDTO;
import br.com.stoom.store.dto.request.BrandRequestDTO;
import br.com.stoom.store.dto.response.BrandResponseDTO;

import java.util.List;

public interface IBrandBO {

    List<BrandResponseDTO> findAll();
    BrandResponseDTO findById(Long id);
    BrandResponseDTO create(BrandCreateDTO brandCreateDTO);
    BrandResponseDTO update(Long id, BrandCreateDTO brandCreateDTO);
    BrandResponseDTO activate(Long id);
    BrandResponseDTO deactivate(Long id);
    void delete(Long id);
}
