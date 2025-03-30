package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500 implements RecommendationRuleSet {

    private final RecommendationRepository recommendationRepository;

    public Invest500(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public List<RecommendationDTO> getRecommendation(String userId) {
        boolean hasDebit = recommendationRepository.hasProductOfType(userId, "DEBIT");
        boolean hasNoInvest = !recommendationRepository.hasProductOfType(userId, "INVEST");
        double savingDeposits = recommendationRepository.getTotalDepositByType(userId, "DEPOSIT", "SAVING");

        if (hasDebit && hasNoInvest && savingDeposits > 1000) {
            Optional<RecommendationDTO> invest500 = Optional.of(new RecommendationDTO(
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    "Invest500",
                    "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
            ));
            return List.of(invest500.get());
        }
        return new ArrayList<>();
    }
}
