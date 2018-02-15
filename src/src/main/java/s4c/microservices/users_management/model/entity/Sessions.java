package s4c.microservices.users_management.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "sessions")
public class Sessions {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name="ip", columnDefinition="varchar(15) default ''")
	private String ip;
	
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private User user;
	
	@Column(name="user_agent", columnDefinition="varchar(100) default ''")
	private String user_agent;
	
	@JsonFormat(pattern="yyyy-mm-dd")
	private Date created_at;
	
	public Sessions(){}
	
	/**
	 * @param id
	 * @param ip
	 * @param user
	 * @param user_agent
	 * @param created_at
	 */
	public Sessions(long id, String ip, User user, String user_agent, Date created_at) {
		this.id = id;
		this.ip = ip;
		this.user = user;
		this.user_agent = user_agent;
		this.created_at = created_at;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUser_agent() {
		return user_agent;
	}
	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	
	

}
