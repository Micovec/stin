package cz.tul.fm.jiri_vokrinek.stin_semestral.dao;

import cz.tul.fm.jiri_vokrinek.stin_semestral.model.Payment;
import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Integer> {

    @Query(value = "SELECT * FROM payment WHERE user_email = ?1", nativeQuery = true)
    List<Payment> getUserPayments(String user_email);
}
