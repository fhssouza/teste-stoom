package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IPriceBO;
import br.com.stoom.store.dto.request.PriceCreateDTO;
import br.com.stoom.store.dto.response.PriceResponseDTO;
import br.com.stoom.store.exception.HandlerNotFoundException;
import br.com.stoom.store.model.Price;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.model.enums.PriceType;
import br.com.stoom.store.repository.PriceRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceBO implements IPriceBO {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public PriceResponseDTO addPriceToProduct(Long productId, PriceCreateDTO priceCreateDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new HandlerNotFoundException("Produto não encontrado"));

        Price price = new Price();
        price.setProduct(product);
        price.setValue(priceCreateDTO.getValue());
        price.setPriceType(PriceType.valueOf(priceCreateDTO.getPriceType()));
        price.setStartDate(priceCreateDTO.getStartDate());
        price.setEndDate(priceCreateDTO.getEndDate());

        Price savedPrice = priceRepository.save(price);

        PriceResponseDTO responseDTO = new PriceResponseDTO();
        responseDTO.setId(savedPrice.getId());
        responseDTO.setValue(savedPrice.getValue());
        responseDTO.setPriceType(savedPrice.getPriceType().toString());
        responseDTO.setStartDate(savedPrice.getStartDate());
        responseDTO.setEndDate(savedPrice.getEndDate());

        return responseDTO;
    }

}
