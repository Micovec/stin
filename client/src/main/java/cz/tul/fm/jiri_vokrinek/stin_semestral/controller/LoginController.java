package cz.tul.fm.jiri_vokrinek.stin_semestral.controller;

import cz.tul.fm.jiri_vokrinek.stin_semestral.data.LoginData;
import cz.tul.fm.jiri_vokrinek.stin_semestral.security.CCUserDetails;
import cz.tul.fm.jiri_vokrinek.stin_semestral.security.MfaAuthentication;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final CodeVerifier verifier;

    private final LoginData loginData;

    private final AuthenticationSuccessHandler successHandler;

    private final AuthenticationFailureHandler failureHandler;

    public LoginController(LoginData loginData,
                           AuthenticationSuccessHandler successHandler,
                           AuthenticationFailureHandler failureHandler) {
        this.loginData = loginData;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;

        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/verify")
    public String verify() {
        return "verify";
    }

    @PostMapping("/verify")
    public void processVerify(@RequestParam("code") String code, MfaAuthentication authentication, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (validateCode(code, authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication.getFirst());
            this.successHandler.onAuthenticationSuccess(request, response, authentication.getFirst());

            return;
        }

        this.failureHandler.onAuthenticationFailure(request, response,
                new BadCredentialsException("Bad credentials"));
    }

    private boolean validateCode(String code, MfaAuthentication authentication) {
        if (authentication.getPrincipal() instanceof CCUserDetails dt) {
            return verifier.isValidCode(dt.getSecret(), code);
        }

        return false;
    }
}
