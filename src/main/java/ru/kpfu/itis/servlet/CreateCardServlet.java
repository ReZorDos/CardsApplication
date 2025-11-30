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
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;

@WebServlet("/api/cards/create-card")
public class CreateCardServlet extends HttpServlet {

    private CardService cardService;
    private OkHttpClient okHttpClient;
    private static final String API_GET_CREATE_OPEN_DOCUMENT = "IP_ДОКУМЕНТОВ/docks/api/documents/open/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
        okHttpClient = (OkHttpClient) config.getServletContext().getAttribute("okHttpClient");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreateCardRequest cardRequest = JsonParser.readRequestBody(req, CreateCardRequest.class);
            Card card = cardService.convertCreateRequestToCardEntity(cardRequest);

            Request request = new  Request.Builder()
                    .url(API_GET_CREATE_OPEN_DOCUMENT + card.getUserId())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    ApiResponse<CardDto> apiResponseFail = ApiResponse.<CardDto>builder()
                            .message("response from documentApplication bad")
                            .data(null)
                            .build();
                    JsonParser.writeResponseBody(apiResponseFail, resp);
                    return;
                }
                String jsonResponse = response.body().string();
                DocumentDto documentDto = JsonParser.fromJson(jsonResponse, DocumentDto.class);
                CardDto cardDto = cardService.saveCard(card, documentDto);

                resp.setStatus(HttpServletResponse.SC_CREATED);
                ApiResponse<CardDto> apiResponseSuccess = ApiResponse.<CardDto>builder()
                        .message("success")
                        .data(cardDto)
                        .build();
                JsonParser.writeResponseBody(apiResponseSuccess, resp);
            }

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
