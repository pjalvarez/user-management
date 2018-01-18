package s4c.microservices.users_management.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import s4c.microservices.users_management.model.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT t FROM User t WHERE (t.name) = ?1")
	User findByUsername(String username);

}