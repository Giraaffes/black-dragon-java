package g_raffes.blackdragon.implement;

import dk.acto.blackdragon.model.Model;
import dk.acto.blackdragon.model.Stats;
import dk.acto.blackdragon.service.ModelTransformer;
import io.vavr.collection.List;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class ModelTransformerImpl implements ModelTransformer<Model, Stats> {
    @Override
    public Stats transform(List<Model> models) {
        BigInteger evenIds = BigInteger.valueOf(models.count(m -> m.getId() % 2 == 0));
        BigInteger oddIds = BigInteger.valueOf(models.size()).subtract(evenIds);

        // As for rounding, RoundingMode.HALF_UP is assumed for mean calculation,
        // and rounding of mean cost in dollars as well as weights is assumed to be unnecessary
        // (in some hypothetical larger model with more records)

        BigDecimal meanCentCost = models
            .map(m -> BigDecimal.valueOf(m.getCost()))
            .reduce(BigDecimal::add)
            .divide(BigDecimal.valueOf(models.size()), 0, RoundingMode.HALF_UP);
        BigDecimal meanDollarCost = meanCentCost
            .divide(BigDecimal.valueOf(100), 0, RoundingMode.UNNECESSARY);

        BigDecimal weightedInventory = models
            .map(m -> BigDecimal.valueOf(m.getInventory())
                .multiply(BigDecimal.valueOf(m.getWeight()).setScale(2, RoundingMode.UNNECESSARY)))
            .reduce(BigDecimal::add);

        BigInteger totalInventory = models
            .map(m -> BigInteger.valueOf(m.getInventory()))
            .reduce(BigInteger::add);

        return Stats.builder()
            .evenIds(evenIds)
            .oddIds(oddIds)
            .meanCost(meanDollarCost)
            .weightedInventory(weightedInventory)
            .totalInventory(totalInventory)
            .build();
    }
}
