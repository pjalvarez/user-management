package s4c.microservices.users_management.model.entity;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;



@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements UserDetails {	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name="name", columnDefinition="varchar(100) default ''")
	private String name = "";
	@NotBlank
	@Column(name="surname", columnDefinition="varchar(100) default ''")
	private String surname = "";
	@NotBlank
	@Column(name="email", columnDefinition="varchar(100) default ''")
	private String email = "";
	
	@JsonFormat(pattern="yyyy-mm-dd")
	private Date date_of_birth;
	@NotBlank
	@Column(name="password", columnDefinition="varchar(255) default ''")
	private String password;
	@Column(name="gender", columnDefinition="varchar(100) default ''")
	private String gender = "";
	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<Assets> assets;
	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<Role> roles;	
	
	
	public User(){}
	
	public User(long id, String name, String password,String surname, String email){
		this.id=id;
		this.name=name;
		this.password=password;
		this.surname=surname;
		this.email = email;

	}
	public User(String name, String password, String surname, String email){
		this.name=name;
		this.password=password;
		this.surname=surname;
		this.email = email;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRole(Role role){
		if(this.getRoles()==null)
			this.setRoles(new ArrayList<Role>());
		this.roles.add(role);
	}
	public void removeRole(Role role){
		if(this.getRoles()!=null)			
			this.roles.remove(role);
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<Assets> getAssets() {
		return assets;
	}
	public void setAssets(ArrayList<Assets> toAdd) {
		this.assets = toAdd;
	}
	public void addAssets(Assets asset){
		if(this.getAssets()==null)
			this.setAssets(new ArrayList<Assets>());
		this.assets.add(asset);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		return authorities;
	}
	@Override
	public String getUsername() {
		return this.name;		
	}
	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setUsername(String username) {
		this.name = username;
		
	}

	public void removeAsset(Assets asset) {
		if(this.assets != null)
			assets.remove(asset);
		
	}

}
