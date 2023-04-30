package cz.tul.fm.jiri_vokrinek.stin_semestral.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
public class SpringSecurityConfig{

    @Bean
    SecurityFilterChain web(HttpSecurity http,
                            AuthorizationManager<RequestAuthorizationContext> mfaAuthorizationManager) throws Exception {

        MfaAuthenticationHandler mfaAuthenticationHandler = new MfaAuthenticationHandler("/verify");

        http.csrf().disable().authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/verify")).access(mfaAuthorizationManager)
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(mfaAuthenticationHandler)
                        .failureHandler(mfaAuthenticationHandler))
                .exceptionHandling((exceptions) -> exceptions.withObjectPostProcessor(new ObjectPostProcessor<ExceptionTranslationFilter>() {
                    @Override
                    public <O extends ExceptionTranslationFilter> O postProcess(O filter) {
                        filter.setAuthenticationTrustResolver(new MfaTrustResolver());
                        return filter;
                    }
                }));
                return http.build();
    }

    @Bean
    AuthorizationManager<RequestAuthorizationContext> mfaAuthorizationManager() {
        return (authentication,
                context) -> new AuthorizationDecision(authentication.get() instanceof MfaAuthentication);
    }

    @Bean
    PasswordEncoder encoder() {
        // For this example we don't care about encryption
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error");
    }
}
