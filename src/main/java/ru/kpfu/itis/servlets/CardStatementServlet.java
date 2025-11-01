package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.request.StatementRequest;
import ru.kpfu.itis.dto.response.StatementResponse;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/card-statement")
public class CardStatementServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatementRequest statementRequest = JsonParser.readRequestBody(req, StatementRequest.class);
        UUID cardId = statementRequest.getCardId();

        StatementResponse statement = cardService.getCardStatement(cardId, statementRequest.getStatementRequest());

        if (statement != null && statement.isSuccess()) {
            JsonParser.writeResponseBody(statement, resp);
        } else {
            JsonParser.writeResponseBody(FieldErrorDto.builder()
                    .error(statement != null ? statement.getMessage() : "Card not found or no transactions")
                    .build(), resp);
        }

    }

}