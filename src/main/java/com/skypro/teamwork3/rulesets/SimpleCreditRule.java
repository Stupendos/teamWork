package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {
    private final UserRepository userRepository;

    public SimpleCreditRule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(String userId) {
        boolean hasNoCredit = !userRepository.hasProductOfType(userId, "CREDIT");
        double debitDeposits = userRepository.getTotalDepositByType(userId,"DEBIT");
        double debitWithdrawals = userRepository.getTotalWithdrawalByType(userId,"DEBIT");

        if (hasNoCredit && debitDeposits > debitWithdrawals && debitWithdrawals > 100_000) {
            return Optional.of(new Recommendation(
                    "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Простой кредит",
                    "Откройте мир выгодных кредитов с нами! Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту." +
                            "Почему выбирают нас:" +
                            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов." +
                            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении." +
                            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
            ));
        }
        return Optional.empty();
    }
}
