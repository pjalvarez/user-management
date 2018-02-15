package s4c.microservices.users_management.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String name;
	
	private String description;
	
	@ManyToMany
	private List<Resource> resources;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private List<User> users;
	
	public Role (){}
	public Role(long id, String name, String description) {

		this.id=id;
		this.name = name;
		this.description = description;
		
	}


	public void addUser(User user){
		if(this.getUsers()==null)
			this.users= new ArrayList<User>();
		
		users.add(user);
	}
	
	public void removeUser(User user){
		if(this.getUsers()==null)
			users.remove(user);
	}
	
	
	public void addResource(Resource resource){
		if(this.getResources() == null)
			this.resources = new ArrayList<Resource>();
		
		resources.add(resource);
	}
	
	
	public void removeResource(Resource resource){
		if(this.getResources() != null)
			resources.remove(resource);
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}


	
	

}
