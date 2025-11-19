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
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("/docks/api/documents/open/" + card.getUserId()))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            DocumentDto documentDto = JsonParser.fromJson(jsonResponse, DocumentDto.class);

            CardDto cardDto = cardService.saveCard(card, documentDto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            ApiResponse<CardDto> apiResponseSuccess = ApiResponse.<CardDto>builder()
                    .message("success")
                    .data(cardDto.builder().build())
                    .build();
            JsonParser.writeResponseBody(apiResponseSuccess, resp);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ApiResponse<CardDto> apiResponseFail = ApiResponse.<CardDto>builder()
                    .message("fail")
                    .data(null)
                    .build();
            JsonParser.writeResponseBody(apiResponseFail, resp);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
