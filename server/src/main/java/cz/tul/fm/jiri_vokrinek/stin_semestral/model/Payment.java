package cz.tul.fm.jiri_vokrinek.stin_semestral.model;

import javax.persistence.*;
import java.time.LocalDate;
@Entity(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private double amount;

    @OneToOne
    private Currency sourceCurrency;

    @OneToOne
    private Currency targetCurrency;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean valid;

    protected Payment() {}

    public Payment(User user, double amount, Currency sourceCurrency, Currency targetCurrency, boolean valid) {
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.user = user;
        this.date = LocalDate.now();
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
