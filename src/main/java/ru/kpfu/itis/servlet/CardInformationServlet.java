package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/api/cards/get-info")
public class CardInformationServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardId = req.getParameter("id");

        //TODO: написать новый метод в сервисе, который достает чистый Card
        Optional<Card> card = cardService.getCardByCardId(UUID.fromString(cardId));

        if (card.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            ApiResponse<Card> apiResponseSuccess = ApiResponse.<Card>builder()
                    .message("success")
                    .data(card.get())
                    .build();
            JsonParser.writeResponseBody(apiResponseSuccess, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ApiResponse<Card> apiResponseFail = ApiResponse.<Card>builder()
                    .message("card not found")
                    .data(null)
                    .build();
            JsonParser.writeResponseBody(apiResponseFail, resp);
        }
    }
}
