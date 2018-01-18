package s4c.microservices.users_management.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4c.microservices.users_management.model.entity.Resource;



@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}