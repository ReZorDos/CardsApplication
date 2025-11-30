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

import java.io.IOException;

@WebServlet("/api/cards/contract")
public class ContractUserServlet extends HttpServlet {

    private OkHttpClient okHttpClient;
    private static final String API_TRANSACTIONS_GET_CONTRACT = "IP_ПРИЛ_ТРАНЗАКЦИЙ/api/transactions/contract?plasticName=";

    @Override
    public void init(ServletConfig config) throws ServletException {
        okHttpClient = (OkHttpClient) config.getServletContext().getAttribute("okHttpClient");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String plasticName = req.getParameter("plasticName");

            Request request = new Request.Builder()
                    .url(API_TRANSACTIONS_GET_CONTRACT + plasticName)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();


            try (Response response = okHttpClient.newCall(request).execute()) {
                resp.setStatus(response.code());
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                if (response.isSuccessful()) {
                    resp.getWriter().write(response.body().string());
                } else {
                    resp.getWriter().write("{\"message\": \"Empty response from TransactionApplication\"}");
                }
            }

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"Internal Server Error\"}");
        }
    }
}
