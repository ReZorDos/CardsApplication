package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/api/cards/users/*")
public class CardAllOfUserServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         try {
            String contextPath = req.getPathInfo();

            String userIdStr = contextPath.substring(1);
            UUID userId = UUID.fromString(userIdStr);

            List<Card> cardOfUser = cardService.getAllCardsOfUser(userId);

            if (cardOfUser.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ApiResponse<Card> emptyResponse = ApiResponse.<Card>builder()
                        .message("cards not found")
                        .data(null)
                        .build();
                JsonParser.writeResponseBody(emptyResponse, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                ApiResponse<List<Card>> successResponse = ApiResponse.<List<Card>>builder()
                        .message("success")
                        .data(cardOfUser)
                        .build();
                JsonParser.writeResponseBody(successResponse, resp);
            }
         } catch (RuntimeException e) {
             resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             ApiResponse<List<Card>> failResponse = ApiResponse.<List<Card>>builder()
                     .message("fail")
                     .data(null)
                     .build();
             JsonParser.writeResponseBody(failResponse, resp);
         }
    }
}
