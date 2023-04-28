package cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentRequestDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PaymentMapper {

    public PaymentRequest paymentRequestDtoToModel(PaymentRequestDto dto) {
        return new PaymentRequest(dto.email, dto.currencyCode, dto.amount);
    }

    public PaymentDto paymentToDto(Payment payment) {
        return new PaymentDto(payment.getAmount(), payment.getCurrency().getCode(), payment.getDate(), payment.isValid());
    }

    public Collection<PaymentDto> toDtos(Collection<Payment> payments) {
        return payments.stream().map(this::paymentToDto).toList();
    }
}
