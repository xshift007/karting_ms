package cl.kartingrm.pricing_service;

import cl.kartingrm.pricing_service.service.DiscountService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountServiceTest {

    private final DiscountService service = new DiscountService();

    @Test
    void groupDiscountCases() {
        assertThat(service.groupDiscount(2)).isEqualTo(0);
        assertThat(service.groupDiscount(4)).isEqualTo(10);
        assertThat(service.groupDiscount(7)).isEqualTo(20);
        assertThat(service.groupDiscount(12)).isEqualTo(30);
    }

    @Test
    void frequentDiscountCases() {
        assertThat(service.frequentDiscount(0)).isEqualTo(0);
        assertThat(service.frequentDiscount(2)).isEqualTo(10);
        assertThat(service.frequentDiscount(5)).isEqualTo(20);
        assertThat(service.frequentDiscount(7)).isEqualTo(30);
    }

    @Test
    void birthdayWinnersLogic() {
        assertThat(service.birthdayWinners(2,1)).isEqualTo(0);
        assertThat(service.birthdayWinners(4,2)).isEqualTo(1);
        assertThat(service.birthdayWinners(8,3)).isEqualTo(2);
    }

    @Test
    void birthdayDiscountEquivalent() {
        assertThat(service.birthdayDiscount(5,2)).isEqualTo(20.0);
        assertThat(service.birthdayDiscount(3,1)).isEqualTo(16.666666666666668);
    }
}
