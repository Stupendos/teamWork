package com.skypro.teamwork3.listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.teamwork3.services.TelegramBotService;
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

    private final TelegramBot telegramBot;

    private final TelegramBotService botService;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot, TelegramBotService botService) {
        this.telegramBot = telegramBot;
        this.botService = botService;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            String msgTxt = update.message() != null ? update.message().text() : null;
            Long chatId = update.message() != null && update.message().chat() != null ? update.message().chat().id() : null;
            String userName = update.message() != null && update.message().from() != null ? update.message().from().username() : null;

            if (msgTxt == null || chatId == null) {
                logger.warn("Received update with null text or chatId. Skipping.");
                return;
            }

            logger.info("Processing update: {}", msgTxt);

            if (msgTxt.equals("/start")) {
                logger.info("Пользователь [{}] (chatId: {}) отправил команду /start", userName, chatId);
                SendMessage sendMessage = new SendMessage(chatId, "Справка для бота будет здесь");
                telegramBot.execute(sendMessage);
            } else if (msgTxt.startsWith("/recommend")) {

                logger.info("contains '/recommend'");

                Pattern pattern = Pattern.compile("/recommend\\s\\S+");
                Matcher matcher = pattern.matcher(msgTxt);

                if (matcher.matches()) {
                    logger.info("correct form");
                    String username = matcher.group(1);
                    logger.info("search recommendations for username: {}", username);
                    SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Пожалуйста, укажите свой юзернейм при написании команды");
                    telegramBot.execute(sendMessage);
                } else if (msgTxt.equals("/recommend")) {
                    logger.info("equals '/recommend', asking for username");
                    SendMessage sendMessage = new SendMessage(chatId, "Укажите свой юзернейм при написании команды");
                    telegramBot.execute(sendMessage);
                } else {
                    logger.info("form is INCORRECT, sending automated response");
                    SendMessage sendMessage = new SendMessage(chatId, "Пожалуйста, убедитесь в корректности написания команды. Она должна выглядеть следующим образом:\r/recommend <Insert_Username_Here>");
                    telegramBot.execute(sendMessage);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
