package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.model.CardProduct;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.util.JsonParser;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/cards/card-products")
public class CardProductsInformationServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardService = (CardService) config.getServletContext().getAttribute("cardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CardProduct> cardProductList = cardService.getAllCardProduct();

        if (!cardProductList.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            ApiResponse<List<CardProduct>> apiResponseSuccess = ApiResponse.<List<CardProduct>>builder()
                    .message("success")
                    .data(cardProductList)
                    .build();
            JsonParser.writeResponseBody(apiResponseSuccess, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ApiResponse<List<CardProduct>> apiResponseFail = ApiResponse.<List<CardProduct>>builder()
                    .message("fail")
                    .data(null)
                    .build();
            JsonParser.writeResponseBody(apiResponseFail, resp);
        }
    }
}
