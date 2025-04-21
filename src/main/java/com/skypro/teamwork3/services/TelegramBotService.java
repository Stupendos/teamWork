package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.exceptions.NoRecommendationFound;
import com.skypro.teamwork3.exceptions.UsernameDontExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TelegramBotService {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);

    private final RecommendationService recService;

    public TelegramBotService(RecommendationService recService) {
        this.recService = recService;
    }

    public String getRecommendationsByUsername(String username) {
        try {
            String fullName = recService.findFullNameByUsername(username);
            logger.info("Processing recommendations by username...");
            List<RecommendationDTO> recList = recService.getRecommendationsByUsername(username);
            logger.trace("Successful recommendations search.");

            if(recList.isEmpty()) {
                throw new NoRecommendationFound(username);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Здравствуйте, ").append(fullName).append("!\r\nРекомендуем Вам следующие продукты:");
            for (RecommendationDTO dto : recList) {
                sb.append("\r\n——— - ").append(dto.getName()).append(" - ———");
                sb.append("\r\n").append(dto.getDescription()).append("\r\n");
            }
            return sb.toString();
        } catch (UsernameDontExistException usernameDontExistException) {
            logger.error(usernameDontExistException.getMessage());
            return "Пользователь не найден. Пожалуйста, проверьте корректность написания юзернейма.";
        } catch (NoRecommendationFound noRecommendationFoundException) {
            logger.error(noRecommendationFoundException.getMessage());
            return "Похоже, что вы уже владеете всеми подходящими вам продуктами. Благодарим за пользование нашими услугами!";
        }
    }
}
