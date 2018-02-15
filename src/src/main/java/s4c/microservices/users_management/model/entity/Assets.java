package s4c.microservices.users_management.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "assets")
public class Assets {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String name;
	
	private String description;
	
	private String type;
	
	@ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE})
	private List<Assets> parents;
	
	@ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE})
	private List<Assets> childrens;
	
	@ManyToMany(mappedBy="assets")
	@JsonIgnore
	private List<User> users;
	

	public  Assets(){}
	
	public  Assets(long id, String name, String description, String type){
		this.id=id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.parents = new ArrayList<Assets>();
		this.childrens = new ArrayList<Assets>();
		this.users = new ArrayList<User>();
	}
	
	
	public void addUser(User user){
		users.add(user);
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Assets> getParents() {
		return parents;
	}
	public void setParents(List<Assets> parents) {
		this.parents = parents;
	}
	public List<Assets> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<Assets> childrens) {
		this.childrens = childrens;
	}

	
	public void addParent(Assets asset_n) {
		if(this.parents == null)
			this.parents= new ArrayList<Assets>();
		
		parents.add(asset_n);
	}
	public void addChildren(Assets asset_n) {
		if(this.childrens == null)
			this.childrens= new ArrayList<Assets>();
		
		childrens.add(asset_n);
	}	
	
	public void removeParent(Assets asset_n) {
		if(this.parents != null)
			parents.remove(asset_n);		
	}

	public void removeChildren(Assets asset_n) {
		if(this.childrens != null)
			childrens.remove(asset_n);
	}

}
