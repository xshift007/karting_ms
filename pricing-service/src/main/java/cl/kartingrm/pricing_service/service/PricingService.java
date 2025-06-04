package cl.kartingrm.pricing_service.service;


import cl.kartingrm.pricing_service.dto.*;
import cl.kartingrm.pricing_service.model.TariffConfig;
import cl.kartingrm.pricing_service.repository.TariffConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final TariffService  tariff;
    private final DiscountService discounts;
    private final ClientService  clients;   // ← sólo si vas a consultar visitas

    public PricingResponse calculate(PricingRequest dto) {

        TariffConfig cfg = tariff.forDate(dto.sessionDate(), dto.laps());
        double baseUnit  = cfg.getPrice();
        int    people    = dto.participants();

        /* === DESCUENTOS === */
        double groupPct   = discounts.groupDiscount(people);
        double freqPct    = discounts.frequentDiscount(dto.clientVisits());
        int    winners    = discounts.birthdayWinners(people, dto.birthdayCount());

        /* === PRECIO FINAL (idéntico al monolito) === */
        double afterGroup = baseUnit * (1 - groupPct/100);
        double ownerUnit  = afterGroup * (1 - freqPct/100);
        double others     = afterGroup * (people - 1 - winners)
                + afterGroup * 0.5 * winners;
        double finalPrice = Math.round(ownerUnit + others);

        return new PricingResponse(
                baseUnit, groupPct, freqPct, winners,
                cfg.getMinutes(), (int) finalPrice,   /* total */
                Math.round((1 - finalPrice/(baseUnit*people))*100)
        );
    }
}

