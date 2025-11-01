package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.request.CreateCardRequest;
import ru.kpfu.itis.entities.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/create-card")
public class CreateCardServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateCardRequest createCardRequest = JsonParser.readRequestBody(req, CreateCardRequest.class);

        Card card = Card.builder()
                .userId(createCardRequest.getUserId())
                .cardProductId(createCardRequest.getCardProductId())
                .plasticName(createCardRequest.getPlasticName())
                .expDate(createCardRequest.getExpDate())
                .cvv(createCardRequest.getCvv())
                .contractName(createCardRequest.getContractName())
                .build();

        cardService.saveCardOfUser(card);

        JsonParser.writeResponseBody(card, resp);

    }


}