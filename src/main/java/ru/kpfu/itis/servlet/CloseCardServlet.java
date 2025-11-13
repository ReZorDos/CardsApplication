package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/api/cards/close-card")
public class CloseCardServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String cardId = req.getParameter("id");

            //TODO:получить документ о закрытии карты у documents
            String closeDocument = "delete document";

            boolean closeCard = cardService.closeCard(UUID.fromString(cardId), closeDocument);

            if (closeCard) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                ApiResponse<Boolean> apiResponseSuccess = ApiResponse.<Boolean>builder()
                        .message("success")
                        .data(true)
                        .build();
                JsonParser.writeResponseBody(apiResponseSuccess, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ApiResponse<Boolean> apiResponseFail = ApiResponse.<Boolean>builder()
                        .message("fail")
                        .data(false)
                        .build();
                JsonParser.writeResponseBody(apiResponseFail, resp);
            }
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ApiResponse<Boolean> apiResponseFail = ApiResponse.<Boolean>builder()
                    .message("fail")
                    .data(false)
                    .build();
            JsonParser.writeResponseBody(apiResponseFail, resp);
        }

    }
}
