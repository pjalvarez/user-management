package s4c.microservices.users_management.model.services;

import java.util.List;

import s4c.microservices.users_management.model.entity.Resource;


public interface IResourceService {
	public List<Resource> listResources();	
	Resource getResourceById(long id);
	Resource getResourceByName(String name);
	Resource addResource(Resource resource);
	void deleteResource(Resource resource);
	void deleteResources(List<Resource> resources);
}
