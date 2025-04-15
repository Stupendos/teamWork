package com.skypro.teamwork3.listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.teamwork3.services.RecommendationService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    TelegramBot telegramBot;
    @Autowired
    RecommendationService recommendationService;

    @PostConstruct
    public void init(){telegramBot.setUpdatesListener(this);}

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            logger.info("Processing update: {}", update.message().text());
            Long chatId = update.message().chat().id();
            String msgTxt = update.message().text();

            if (msgTxt.equals("/start")) {
                logger.info("--- /start ---");
                SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Справка для бота будет здесь");
                telegramBot.execute(sendMessage);
            } else if (msgTxt.contains("/recommend")) {

                logger.info("contains '/recommend'");

                if (msgTxt.equals("/recommend")) {
                    logger.info("equals '/recommend', asking for username");
                    SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Пожалуйста, укажите свой юзернейм при написании команды");
                    telegramBot.execute(sendMessage);
                }
                else {
                    logger.info("checks regex for correct form");
                    Pattern pattern = Pattern.compile("/recommend\\s\\S+"); // --- /recommend[space][some letters, numbers or underscores]
                    Matcher matcher = pattern.matcher(msgTxt);

                    if (matcher.matches()) {
                        logger.info("the form is correct");
                        String username = msgTxt.substring(11); // --- "/recommend [username]"
                        username = username.strip();
                        /*logger.info("attempting to fetch full name");
                        String userFullName = recommendationService.getFullName(username);*/
                        logger.info("attempting to find recommendations");
                        SendMessage sendMessage = new SendMessage(chatId, recommendationService.getRecommendationsByUsername(username));
                        telegramBot.execute(sendMessage);
                    } else {
                        logger.info("form is INCORRECT, sending automated response");
                        SendMessage sendMessage = new SendMessage(chatId, "Пожалуйста, убедитесь в корректности написания команды. Она должна выглядеть следующим образом:\r/recommend <Insert_Username_Here>");
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
