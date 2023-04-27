package cz.tul.fm.jiri_vokrinek.stin_semestral.dao;

import cz.tul.fm.jiri_vokrinek.stin_semestral.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {

}
