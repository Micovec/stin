package cz.tul.fm.jiri_vokrinek.stin_semestral.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.data.AccountData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.dto.PaymentDto;
import cz.tul.fm.jiri_vokrinek.stin_semestral.security.CCUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountData accountData;

    public AccountController(AccountData accountData) {
        this.accountData = accountData;
    }

    @GetMapping
    public String getAccounts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CCUserDetails details) {
            model.addAttribute("accounts", accountData.getUserAccounts(details.getUsername()));

            return "account";
        }

        return "redirect:/";
    }
}
