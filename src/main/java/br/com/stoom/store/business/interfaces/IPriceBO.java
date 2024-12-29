package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.request.PriceCreateDTO;
import br.com.stoom.store.dto.response.PriceResponseDTO;

public interface IPriceBO {

    PriceResponseDTO addPriceToProduct(Long productId, PriceCreateDTO priceCreateDTO);
}
