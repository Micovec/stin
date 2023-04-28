package cz.tul.fm.jiri_vokrinek.stin_semestral.api.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.EmailDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.dto.PaymentRequestDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.api.mapper.PaymentMapper;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.supporting.PaymentRequest;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.PaymentService;
import cz.tul.fm.jiri_vokrinek.stin_semestral.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    private final PaymentMapper paymentMapper;

    public PaymentController(PaymentService paymentService, UserService userService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.userService = userService;
        this.paymentMapper = paymentMapper;
    }

    @PostMapping
    public PaymentDto pay(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentRequest request = paymentMapper.paymentRequestDtoToModel(paymentRequestDto);
        Payment payment = paymentService.pay(request);

        return paymentMapper.paymentToDto(payment);
    }

    @PostMapping("/records")
    public Collection<PaymentDto> getPayments(@RequestBody EmailDto emailDto) {
        Optional<User> user = userService.readById(emailDto.email);
        if (user.isEmpty()) {
            return new ArrayList<>();
        }

        return paymentMapper.toDtos(paymentService.getUserPayments(user.get()));
    }
}
