package ru.kpfu.itis.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.config.DataBaseConfig;
import ru.kpfu.itis.mapper.CardMapper;
import ru.kpfu.itis.repository.CardRepository;
import ru.kpfu.itis.repository.impl.CardRepositoryImpl;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.service.impl.CardServiceImpl;

@WebListener
public class ProjectStartUpListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        CardRepository cardRepository = new CardRepositoryImpl(DataBaseConfig.getJdbcTemplate());
        CardMapper cardMapper = new CardMapper();
        CardService cardService = new CardServiceImpl(cardRepository, cardMapper);
        context.setAttribute("cardService", cardService);


    }
}
