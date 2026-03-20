package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CardProduct>> getCardsProducts() {
        try {
            List<CardProduct> cardProductList = cardService.getAllCardProduct();
            if (!cardProductList.isEmpty()) {
                return new ResponseEntity<>(cardProductList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<CardProduct> getCardProduct(@PathVariable("id") UUID id) {
        try {
            Optional<CardProduct> cardProduct = cardService.getCardProductById(id);
            if(cardProduct.isPresent()) {
                return new ResponseEntity<>(cardProduct.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }
    }

}
