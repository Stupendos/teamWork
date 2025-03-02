package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TopSavingRule implements RecommendationRuleSet {
    private final UserRepository userRepository;

    public TopSavingRule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<Recommendation> getRecommendation(String userId) {
        boolean hasDebit = userRepository.getProductTypes(userId).contains("DEBIT");
        double debitDeposits = userRepository.getProductBalances(userId).getOrDefault("DEBIT", 0.0);
        double savingDeposits = userRepository.getProductBalances(userId).getOrDefault("SAVING", 0.0);
        double debitExpenses = userRepository.getProductExpenses(userId).getOrDefault("DEBIT", 0.0);

        if (hasDebit &&
                (debitDeposits >= 50_000 || savingDeposits >= 50_000)
                && debitDeposits > debitExpenses) {
            return Optional.of(new Recommendation(
                    "59efc529-2fff-41af-baff-90ccd7402925",
                    "Top saving",
                    "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +
                            "Преимущества «Копилки»:" +
                            "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет." +
                            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости." +
                            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг." +
                            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"
            ));
        }
        return Optional.empty();
    }
}
