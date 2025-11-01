package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/getListCardProduct")
public class ListCardProductServlet extends HttpServlet {

    private CardProductService cardProductService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cardProductService = (CardProductService) config.getServletContext().getAttribute("cardProductService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardProductRequest cardProductRequest = readCardProduct(req, CardProductRequest.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
