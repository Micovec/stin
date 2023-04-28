package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentRequestDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.PaymentMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting.PaymentRequest;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentMapper paymentMapper;

    public PaymentController(PaymentService paymentService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    @PostMapping("/payment")
    public PaymentDto pay(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentRequest request = paymentMapper.paymentRequestDtoToModel(paymentRequestDto);
        Payment payment = paymentService.pay(request);

        return paymentMapper.paymentToDto(payment);
    }
}
