package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;

@WebServlet("/api/cards/create-card")
public class CreateCardServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreateCardRequest cardRequest = JsonParser.readRequestBody(req, CreateCardRequest.class);
            Card card = cardService.convertCreateRequestToCardEntity(cardRequest);
            CardDto cardDto = cardService.saveCard(card);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            ApiResponse<CardDto> apiResponseSuccess = ApiResponse.<CardDto>builder()
                    .message("success")
                    .data(cardDto)
                    .build();
            JsonParser.writeResponseBody(apiResponseSuccess, resp);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ApiResponse<CardDto> apiResponseFail = ApiResponse.<CardDto>builder()
                    .message("fail")
                    .data(null)
                    .build();
            JsonParser.writeResponseBody(apiResponseFail, resp);
        }

    }
}
