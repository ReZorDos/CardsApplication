package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
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

        Optional<CardDto> cardDto = cardService.getCardByCardId(UUID.fromString(cardId));

        if (cardDto.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            ApiResponse<CardDto> apiResponseSuccess = ApiResponse.<CardDto>builder()
                    .message("success")
                    .data(cardDto.get())
                    .build();
            JsonParser.writeResponseBody(apiResponseSuccess, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ApiResponse<CardDto> apiResponseFail = ApiResponse.<CardDto>builder()
                    .message("card not found")
                    .data(null)
                    .build();
            JsonParser.writeResponseBody(apiResponseFail, resp);
        }
    }
}
