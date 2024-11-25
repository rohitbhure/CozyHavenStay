package com.hexaware.CozyHavenStay.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);
    
    Optional<User> findByName(String name);

    // Custom query to find all users created after a specific date
    @Query("SELECT u FROM User u WHERE u.createdAt > :date")
    List<User> findUsersCreatedAfter(@Param("date") LocalDateTime date);

    // Native query to count total users
    @Query(value = "SELECT COUNT(*) FROM user", nativeQuery = true)
    Long countTotalUsers();
    
	Boolean existsByEmail(String email);

	Optional<User> findBynameOrEmail(String name, String email);

	

}