package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.model.Product;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.model.UserTransactionData;
import com.skypro.teamwork3.repository.RecommendationsRepository;
import com.skypro.teamwork3.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Invest500 implements RecommendationRuleSet {

    private final UserRepository userRepository;

    public Invest500(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(String userId) {
        boolean hasDebit = userRepository.hasProductOfType(userId,"DEBIT");
        boolean hasNoInvest = !userRepository.hasProductOfType(userId,"INVEST");
        double savingDeposits = userRepository.getTotalDepositByType(userId,"SAVING");

        if (hasDebit && hasNoInvest && savingDeposits > 1000) {
            return Optional.of(new Recommendation(
                    "147f6a0f-3b91-413b-ab99-87f081d60d5a",
                    "Invest500",
                    "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
            ));
        }
        return Optional.empty();
    }
}
