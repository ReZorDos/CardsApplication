package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/api/cards/close-card")
public class CloseCardServlet extends HttpServlet {

    private CardService cardService;
    private OkHttpClient okHttpClient;
    private static final String API_DOCUMENTS_GET_CREATE_CLOSE_DOCUMENT = "IP_ДОКУМЕНТОВ/docks/api/documents/close/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
        okHttpClient = (OkHttpClient) config.getServletContext().getAttribute("okHttpClient");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String cardId = req.getParameter("id");
            Optional<CardDto> card = cardService.getCardDtoByCardId(UUID.fromString(cardId));

            Request request = new Request.Builder()
                    .url(API_DOCUMENTS_GET_CREATE_CLOSE_DOCUMENT + card.get().getUserId())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    ApiResponse<Boolean> apiResponseSuccess = ApiResponse.<Boolean>builder()
                            .message("response from documentApplication bad")
                            .data(true)
                            .build();
                    JsonParser.writeResponseBody(apiResponseSuccess, resp);
                    return;
                }
                String jsonResponse = response.body().string();
                DocumentDto documentDto = JsonParser.fromJson(jsonResponse, DocumentDto.class);

                boolean closeCard = cardService.closeCard(UUID.fromString(cardId), documentDto.getId());

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
