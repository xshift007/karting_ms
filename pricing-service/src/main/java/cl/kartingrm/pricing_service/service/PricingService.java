package cl.kartingrm.pricing_service.service;


import cl.kartingrm.pricing_service.dto.*;
import cl.kartingrm.pricing_service.model.TariffConfig;
import cl.kartingrm.pricing_service.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final TariffService tariffService;
    private final DiscountService disc;
    private final WebClient web;

    /**
     * Calcula el precio final de una sesión considerando descuentos por
     * tamaño de grupo, frecuencia y celebración de cumpleaños.
     * <p>
     * Para simplificar el cálculo se asume que el titular de la reserva
     * nunca es considerado como "cumpleañero" al aplicar el descuento del
     * 50&nbsp;% correspondiente. Si el titular efectivamente está de
     * cumpleaños deberá reservar a nombre de otra persona para optar al
     * beneficio.
     */
    public PricingResponse calculate(PricingRequest dto) {

        TariffConfig cfg = tariffService.forDate(dto.sessionDate(), dto.laps());

        double base = cfg.getPrice();
        double groupPct = disc.groupDiscount(dto.participants());

        int visits = web.get()
                .uri("http://client-service/api/clients/{email}/visits", dto.clientEmail())
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        double freqPct  = disc.frequentDiscount(visits);
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


