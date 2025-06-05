package cl.kartingrm.pricing_service.service;


import cl.kartingrm.pricing_service.dto.*;
import cl.kartingrm.pricing_service.model.TariffConfig;
import cl.kartingrm.pricing_service.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final TariffService tariffService;
    private final DiscountService disc;

    public PricingResponse calculate(PricingRequest dto) {

        TariffConfig cfg = tariffService.forDate(dto.sessionDate(), dto.laps());

        double base = cfg.getBasePrice();
        double groupPct = disc.groupDiscount(dto.participants());
        double freqPct  = disc.frequentDiscount(dto.clientVisits());
        int    winners  = disc.birthdayWinners(dto.participants(), dto.birthdayCount());

        // precio después de grupo y frecuencia (solo cambia titular)
        double afterGroup = base * (1 - groupPct/100);
        double ownerUnit  = afterGroup * (1 - freqPct/100);
        double regular    = afterGroup;

        // aplicar 50 % a cumpleañeros
        int winnersLeft = winners;
        if (winnersLeft > 0) {             // asumimos titular NO cumpleañero
            ownerUnit *= 0.5;
            winnersLeft--;
        }
        double rest = (dto.participants() - 1 - winnersLeft) * regular
                + winnersLeft * regular * 0.5;

        int finalTotal = (int) Math.round(ownerUnit + rest);
        double totalDisc = (base * dto.participants() - finalTotal) * 100
                / (base * dto.participants());

        return new PricingResponse(
                base,
                groupPct,
                freqPct,
                winners,
                cfg.getMinutes(),
                finalTotal,
                totalDisc);
    }
}


