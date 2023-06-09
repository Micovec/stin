package cz.tul.fm.jiri_vokrinek.stin_semestral.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
public class User implements Serializable {

    @Id
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String password;

    private String secret;

    @OneToMany(targetEntity = Account.class, cascade = CascadeType.REMOVE)
    private Set<Account> accounts = new HashSet<>();

    protected User() {}

    public User(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = "USER";
        this.password = password;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getSecret() {
        return secret;
    }
}
