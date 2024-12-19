package com.hexaware.CozyHavenStay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.Enum.Roles;
import com.hexaware.CozyHavenStay.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);

	List<User> findByRole(Roles role);
}
