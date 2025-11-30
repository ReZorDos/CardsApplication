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
import java.util.Optional;
import java.util.UUID;

@WebServlet("/api/cards/get-all-info")
public class CardAllInformationServlet extends HttpServlet {

    private CardService cardService;
    private OkHttpClient okHttpClient;
    private static final String API_DOCUMENTS_GET_OPEN_DOCUMENT = "IP_ДОКУМЕНТОВ/docks/api/documents/user/open/";
    private static final String API_DOCUMENTS_GET_CLOSE_DOCUMENT = "IP_ДОКУМЕНТОВ/docks/api/documents/user/close/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
        okHttpClient = (OkHttpClient) config.getServletContext().getAttribute("okHttpClient");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getPathInfo();
        String cardId = contextPath.substring(1);

        Optional<CardDto> cardDto = cardService.getCardDtoByCardId(UUID.fromString(cardId));

        Request requestOpenDocument = new Request.Builder()
                .url(API_DOCUMENTS_GET_OPEN_DOCUMENT + cardId)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = okHttpClient.newCall(requestOpenDocument).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                DocumentDto documentDto = JsonParser.fromJson(jsonResponse, DocumentDto.class);
                cardDto.get().setOpenDocument(documentDto);
            }
        }

        Request requestCloseDocument = new Request.Builder()
                .url(API_DOCUMENTS_GET_CLOSE_DOCUMENT + cardId)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try (Response response = okHttpClient.newCall(requestCloseDocument).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                DocumentDto documentDto = JsonParser.fromJson(jsonResponse, DocumentDto.class);
                cardDto.get().setCloseDocument(documentDto);
            }
        }

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
