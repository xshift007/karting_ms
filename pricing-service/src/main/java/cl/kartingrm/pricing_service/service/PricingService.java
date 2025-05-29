package cl.kartingrm.pricing_service.service;


import cl.kartingrm.pricing_service.dto.*;
import cl.kartingrm.pricing_service.model.TariffConfig;
import cl.kartingrm.pricing_service.repository.TariffConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class PricingService {

    private final TariffConfigRepository repo;

    public PricingResponse calculate(PricingRequest req) {
        TariffConfig tariff = repo.findByLaps(req.laps())
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada"));

        int price = tariff.getBasePrice();
        // recargo por fin de semana / feriado
        if (req.weekend() || req.holiday()) price *= 1.10;

        // descuento por tamaño de grupo
        int groupDisc = switch (req.participants()) {
            case 3,4,5 -> 10;
            case 6,7,8,9,10 -> 20;
            case 11,12,13,14,15 -> 30;
            default -> 0;
        };

        // descuento cliente frecuente
        int visitDisc = switch (req.clientVisits()) {
            case 2,3,4 -> 10;
            case 5,6 -> 20;
            default -> (req.clientVisits() >= 7 ? 30 : 0);
        };

        // descuento cumpleaños (si hay cumpleañeros y grupo cumple la regla)
        int bdayDisc = (req.birthdayCount() > 0) ? 50 : 0;

        int discount = Math.max(groupDisc, visitDisc);   // se aplicará el mayor
        discount = Math.max(discount, bdayDisc);

        int finalPrice = (int) Math.round(price * (100 - discount) / 100.0);

        return new PricingResponse(price, discount, finalPrice);
    }
}
