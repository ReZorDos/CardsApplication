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
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/api/cards/get-all-info")
public class CardAllInformationServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String cardId = req.getParameter("id");

            Optional<CardDto> cardDto = cardService.getCardDtoByCardId(UUID.fromString(cardId));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("/docks/api/documents/user/open/" + cardId))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponseOpenDocument = response.body();
            DocumentDto openDocumentDto = JsonParser.fromJson(jsonResponseOpenDocument, DocumentDto.class);
            cardDto.get().setOpenDocument(openDocumentDto);

            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("/docks/api/documents/user/close/" + cardId))
                    .build();
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            String jsonResponseCloseDocument = response1.body();
            DocumentDto closeDocumentDto = JsonParser.fromJson(jsonResponseCloseDocument, DocumentDto.class);
            cardDto.get().setCloseDocument(closeDocumentDto);

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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
