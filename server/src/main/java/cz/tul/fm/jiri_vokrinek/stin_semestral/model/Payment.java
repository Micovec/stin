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
    private Currency currency;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean valid;

    protected Payment() {}

    public Payment(User user, double amount, Currency currency, boolean valid) {
        this.amount = amount;
        this.currency = currency;
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

    public Currency getCurrency() {
        return currency;
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
