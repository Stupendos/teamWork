package com.skypro.teamwork3.listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.teamwork3.services.TelegramBotService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            String msgTxt = update.message().text();
            Long chatId = update.message().chat().id();

            if (msgTxt == null || msgTxt.isEmpty()) {
                logger.warn("Received update with null text or chatId. Skipping.");
                return;
            }
            logger.info("Processing update: '{}'", msgTxt);
            Pattern pattern = Pattern.compile("(/\\w+)(\\s*)(\\S*)");
            Matcher matcher = pattern.matcher(msgTxt);
            matcher.matches();
            if (matcher.group(1).equals("/start")) {
                logger.info("chatId: {} отправил команду /start", chatId);
                SendMessage sendMessage = new SendMessage(chatId, "Справка для бота будет здесь");
                telegramBot.execute(sendMessage);
            } else if (matcher.group(1).equals("/recommend")) {
                String username = matcher.group(3);
                SendMessage sendMessage;
                if (username.isBlank()) {
                    sendMessage = new SendMessage(chatId, "Пожалуйста, укажите свой юзернейм при написании команды");
                    telegramBot.execute(sendMessage);
                } else {
                    logger.info("search recommendations for username: {}", username);
                    sendMessage = new SendMessage(chatId, botService.getRecommendationsByUsername(username));
                    telegramBot.execute(sendMessage);
                }
            } else {
                SendMessage sendMessage = new SendMessage(chatId, "Команда не обнаружена. Пожалуйста, убедитесь в корректности написания команды.");
                telegramBot.execute(sendMessage);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
