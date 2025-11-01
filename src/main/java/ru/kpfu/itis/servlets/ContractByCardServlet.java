package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.entities.Card;
import ru.kpfu.itis.repository.CardRepository;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet("/account-by-card")
public class ContractByCardServlet extends HttpServlet {

    private CardRepository cardRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardRepository = (CardRepository) config.getServletContext().getAttribute("cardRepository");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonParser.writeResponseBody(Map.of("success", false, "message", "Card ID is required"), resp);
            return;
        }

        try {
            UUID cardId = UUID.fromString(pathInfo.substring(1));
            Card card = cardRepository.getInformationOfCard(cardId);

            if (card == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonParser.writeResponseBody(Map.of("success", false, "message", "Card not found"), resp);
                return;
            }

            Map<String, Object> accountData = new HashMap<>();
            accountData.put("success", true);
            accountData.put("message", "Account retrieved successfully");
            accountData.put("contractName", card.getContractName());
            accountData.put("cardId", cardId.toString());

            JsonParser.writeResponseBody(accountData,resp);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonParser.writeResponseBody(Map.of("success", false, "message", e.getMessage()), resp);
        }
    }

}
