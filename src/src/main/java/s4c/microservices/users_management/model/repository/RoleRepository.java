package s4c.microservices.users_management.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import s4c.microservices.users_management.model.entity.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("SELECT t FROM Role t WHERE UCASE(t.name) = ?1")
	Role findByName(String name);

}