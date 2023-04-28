package cz.tul.fm.jiri_vokrinek.stin_semestral.model;

import javax.persistence.*;

@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private User user;

    @OneToOne
    private Currency currency;

    private double balance;

    protected Account() {}

    public Account(User user, Currency currency) {
        this.user = user;
        this.currency = currency;
        this.balance = 0;
    }

    public void add(double amount) {
        balance += amount;
    }

    public int getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }
}
