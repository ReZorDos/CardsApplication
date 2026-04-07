package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.model.CardProduct;
import ru.kpfu.itis.service.CardService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v2/cards")
@RequiredArgsConstructor
public class ProductController {

    private final CardService cardService;

    @GetMapping("/products")
    public List<CardProduct> getCardsProducts() {
        List<CardProduct> cardProductList = cardService.getAllCardProduct();
        log.info("Получил все карточные продукты");
        return cardProductList;
    }

    @GetMapping("/products/{id}")
    public CardProduct getCardProduct(@PathVariable("id") UUID id) {
        Optional<CardProduct> cardProduct = cardService.getCardProductById(id);
        log.info("Получил карточный продукт по id: {}", id);
        return cardProduct.get();
    }

}
