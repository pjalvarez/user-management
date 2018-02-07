package s4c.microservices.users_management.model.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4c.microservices.users_management.model.entity.Resource;
import s4c.microservices.users_management.model.repository.ResourceRepository;

@Service
public class ResourceService implements IResourceService {

	@Autowired
	private ResourceRepository resourceRepository;


	/**
	 * Returns a complete list of Assets
	 */
	@Override
	public List<Resource> listResources() {
		return resourceRepository.findAll();

	}

	@Override
	public Resource getResourceById(long id) {
		return resourceRepository.findOne(id);
	}
	@Override
	public Resource getResourceByName(String name) {
		return resourceRepository.findByName(name);
	}
	
	@Override
	public Resource addResource(Resource resource) {		
		return resourceRepository.saveAndFlush(resource);		
	}
	
	@Override
	public void deleteResources(List<Resource> resources){
		this.resourceRepository.deleteInBatch(resources);
		this.resourceRepository.flush();
	}

	@Override
	public void deleteResource(Resource resource){
		this.resourceRepository.delete(resource);
		this.resourceRepository.flush();
	}
	

}
