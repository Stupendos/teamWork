package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Invest500Test {
    private UserRepository userRepository;
    private Invest500 invest500;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        invest500 = new Invest500(userRepository);
    }

    @Test
    void shouldReturnRecommendation_whenConditionsAreMet() {
        // Настраиваем поведение мока
        String userId = "user1";
        when(userRepository.hasProductOfType(userId, "DEBIT")).thenReturn(true);
        when(userRepository.hasProductOfType(userId, "INVEST")).thenReturn(false);
        when(userRepository.getTotalDepositByType(userId, "SAVING")).thenReturn(1500.0);

        Optional<Recommendation> recommendation = invest500.getRecommendation(userId);

        assertTrue(recommendation.isPresent());
        assertEquals("Invest500", recommendation.get().getName());
        assertEquals("Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!",
                recommendation.get().getText());
    }

    @Test
    void shouldReturnEmpty_whenConditionsAreNotMet() {
        // Настраиваем поведение мока
        String userId = "user1";
        when(userRepository.hasProductOfType(userId, "DEBIT")).thenReturn(false);
        when(userRepository.hasProductOfType(userId, "INVEST")).thenReturn(false);
        when(userRepository.getTotalDepositByType(userId, "SAVING")).thenReturn(1500.0);

        Optional<Recommendation> recommendation = invest500.getRecommendation(userId);

        assertTrue(recommendation.isEmpty());
    }
}