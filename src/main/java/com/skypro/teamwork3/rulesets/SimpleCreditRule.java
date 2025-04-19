package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {
    private final RecommendationRepository recommendationRepository;

    public SimpleCreditRule(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public List<RecommendationDTO> getRecommendation(String userId) {
        boolean hasNoCredit = !recommendationRepository.hasProductOfType(userId, "CREDIT");
        double debitDeposits = recommendationRepository.getTotalDepositByType(userId, "DEPOSIT", "DEBIT");
        double debitWithdrawals = recommendationRepository.getTotalDepositByType(userId, "WITHDRAW", "DEBIT");

        if (hasNoCredit && debitDeposits > debitWithdrawals && debitWithdrawals > 100_000) {
            return List.of(new RecommendationDTO(
                    "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Простой кредит",
                    "Откройте мир выгодных кредитов с нами! Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту." +
                            "Почему выбирают нас:" +
                            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов." +
                            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении." +
                            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!",
                    new ArrayList<>()
            ));
        }
        return new ArrayList<>();
    }
}




