package s4c.microservices.users_management.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import s4c.microservices.users_management.model.entity.Resource;




@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

	@Query("SELECT t FROM Resource t WHERE UCASE(t.name) = ?1")
	Resource findByName(String name);
	
}