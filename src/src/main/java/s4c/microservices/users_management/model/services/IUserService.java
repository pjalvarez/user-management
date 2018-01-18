package s4c.microservices.users_management.model.services;

import java.util.List;

import s4c.microservices.users_management.model.entity.User;

public interface IUserService {

	List<User> listUsers();

}
