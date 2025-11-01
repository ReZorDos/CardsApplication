package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.response.SuccessResponse;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/close-card")
public class CloseCardServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CloseCardRequest closeCardRequest = JsonParser.readRequestBody(req, CloseCardRequest.class);
        UUID cardId = closeCardRequest.getCardId();

        boolean isClosed = cardService.closeCardOfUser(cardId);

        if (isClosed) {
            JsonParser.writeResponseBody(SuccessResponse.builder().response("Card successfully closed").build(), resp);
        } else {
            JsonParser.writeResponseBody(FieldErrorDto.builder().error("Card not found or already closed").build(), resp);
        }
    }

}