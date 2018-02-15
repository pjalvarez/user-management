package s4c.microservices.users_management.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s4c.microservices.users_management.model.entity.Resource;
import s4c.microservices.users_management.model.entity.Role;
import s4c.microservices.users_management.model.repository.RoleRepository;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ResourceService resourceService;

	/**
	 * Returns a complete list of Assets
	 */
	@Override
	public List<Role> listRoles() {

		return roleRepository.findAll();

	}
	
	public boolean isResourcesOk(Role role){
		boolean exist = true;
		for(Resource resource : role.getResources()){				
			Resource res = resourceService.getResourceByName(resource.getName());
			if(res == null){
				exist = false;
				break;
			} 			
		}		
		return exist;
	}
	
	public Role postRole(Role role){
		if (role != null) {	
			
			ArrayList<Resource> toAdd=new ArrayList<Resource>();
			boolean exist = true;
			for(Resource resource : role.getResources()){				
				Resource res = resourceService.getResourceByName(resource.getName());
				if(res == null){
					exist = false;
					break;
				} else {
					toAdd.add(res);
				}				
			}			
			
			if(exist) {
				role.setResources(toAdd);				
				return roleRepository.saveAndFlush((role));
			}
		}		
		return null;
	}


	@Override
	public Role getRoleById(long id) {
		return roleRepository.findOne(id);
	}	
	@Override
	public Role getRoleByName(String name){
		return roleRepository.findByName(name);
	}

	@Override
	public boolean addResources(Role role, List<Resource> resources) {
		for(Resource resource : resources){
			if(!roleHasResource(role,resource)){
				
				//search for resource to avoid duplicates.
				Resource fromDatabase = this.resourceService.getResourceByName(resource.getName());
				if(fromDatabase!=null)
					resource = fromDatabase;
				
				resourceService.addResource(resource);
				role.addResource(resource);
			}
		}
		roleRepository.saveAndFlush(role);
		return true;
	}
	
	
	
	
	
	/**
	 * 
	 * @param role role
	 * @param resource resource
	 * @return boolean
	 */
	private boolean roleHasResource(Role role, Resource resource){
		boolean response = false;
		for(Resource res : role.getResources()){
			if(res.getName().toLowerCase().equals(resource.getName().toLowerCase())){
				response =  true;
				break;
			}
		}
		return response;
	}
	
	
	/**
	 * 
	 * @param role
	 * @param request
	 * @return
	 */
	public boolean updateRole(Role role, Role request) {
		
		if(request.getName()!=null)
			role.setName(request.getName());
		if(request.getDescription()!=null)
			role.setDescription(request.getDescription());
		if(request.getResources()!=null){
			role = this.getRidOfResources(role, request);
		}
		
		/*Not include in request model*/
		if(request.getUsers()!=null){
			
		}
		
		roleRepository.saveAndFlush(role);
		return true;
		
	}
	
	
	private Role getRidOfResources(Role original, Role nObject) {
		if (nObject.getResources() != null) {
			ArrayList<Resource> newResources = new ArrayList<Resource>();
			ArrayList<Resource> toRemoveResources = new ArrayList<Resource>();
			for (Resource resource_original : original.getResources()) {
				boolean hasResource = false;
				for (Resource resource : nObject.getResources()) {
					if (resource_original.getName().toLowerCase().equals(resource.getName().toLowerCase())) {
						if(resource.getDescription()!=null){
							if(!resource_original.getDescription().equals(resource.getDescription())){
								resource_original.setDescription(resource.getDescription());
							}
						}
						hasResource = true;
						break;
					}
				}
				if (hasResource) {
					newResources.add(resource_original);
				} else {
					resource_original.setUsers(null);
					toRemoveResources.add(resource_original);
				}
			}

			for (Resource resource : nObject.getResources()) {
				boolean hasResource = false;
				for (Resource resource_original : original.getResources()) {
					if (resource_original.getName().toLowerCase().equals(resource.getName().toLowerCase())) {
						hasResource = true;
						break;
					}
				}
				if (!hasResource) {
					resourceService.addResource(resource);
					newResources.add(resource);
				}
			}

			original.getResources().removeAll(toRemoveResources);
			original.setResources(newResources);

			// Delete useless entities.	
			for(Resource resource : toRemoveResources){				
				original.removeResource(resource);
				this.resourceService.deleteResource(resource);
			}
		}
		return original;
	}

	public Boolean deleteRole(Long roleId) {
		Role role = this.roleRepository.findOne(roleId);
		if(role!=null) {
			roleRepository.delete(role);
			return true;
		}		
		
		return false;
	}

	public void removeResource(Role role, Resource resource) {
		role.getResources().remove(resource);
		roleRepository.saveAndFlush(role);
		
	}
}
