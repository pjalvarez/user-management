package s4c.microservices.users_management.model.services;

import java.util.List;

import s4c.microservices.users_management.model.entity.Resource;
import s4c.microservices.users_management.model.entity.Role;

public interface IRoleService {
	public List<Role> listRoles();
	Role getRoleByName(String name);
	Role getRoleById(long id);
	boolean addResources(Role role, List<Resource> resources);
}
