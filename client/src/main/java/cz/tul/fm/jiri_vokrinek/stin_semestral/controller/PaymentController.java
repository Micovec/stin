package cz.tul.fm.jiri_vokrinek.stin_semestral.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.data.AccountData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.data.CurrencyData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.data.PaymentData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.PaymentRequestDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.security.CCUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final CurrencyData currencyData;
    private final PaymentData paymentData;
    private final AccountData accountData;

    public PaymentController(CurrencyData currencyData, PaymentData paymentData, AccountData accountData) {
        this.currencyData = currencyData;
        this.paymentData = paymentData;
        this.accountData = accountData;
    }

    @GetMapping()
    public String getPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CCUserDetails details) {
            model.addAttribute("currencies", currencyData.getAll());
            model.addAttribute("accountCurrencies", accountData.getUserAccounts(details.getUsername()));

            return "payment";
        }

        return "redirect:/";
    }

    @PostMapping
    public String pay(@ModelAttribute PaymentRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CCUserDetails details) {
            PaymentDto paymentDto = paymentData.pay(details.getUsername(), dto.amount, dto.currencyCode, dto.targetCurrencyCode);

            if (paymentDto.valid) {
                return "redirect:/";
            }
            return "redirect:/payment/invalid";
        }

        return "redirect:/";
    }

    @GetMapping("/invalid")
    public String getInvalid() {
        return "invalidPayment";
    }

    @GetMapping("/records")
    public String showRecords(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CCUserDetails details) {
            model.addAttribute("payments", paymentData.getPayments(details.getUsername()));

            return "/records";
        }

        return "redirect:/";
    }
}
