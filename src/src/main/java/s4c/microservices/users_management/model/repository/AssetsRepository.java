package s4c.microservices.users_management.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import s4c.microservices.users_management.model.entity.Assets;



@Repository
public interface AssetsRepository extends JpaRepository<Assets, Long> {

	@Query("SELECT t FROM Assets t WHERE UCASE(t.name) = ?1 ")
	List<Assets> findByName(String name);

}