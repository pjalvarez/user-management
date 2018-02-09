package s4c.microservices.users_management.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="PingRequest", description="request data")
public class changePasswordRequest {

    
    private String old_password;
    private String new_password1;
    private String new_password2;

    
    public changePasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the old_password
	 */
	public String getOld_password() {
		return old_password;
	}


	/**
	 * @param old_password the old_password to set
	 */
	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}


	/**
	 * @return the new_password1
	 */
	public String getNew_password1() {
		return new_password1;
	}


	/**
	 * @param new_password1 the new_password1 to set
	 */
	public void setNew_password1(String new_password1) {
		this.new_password1 = new_password1;
	}


	/**
	 * @return the new_password2
	 */
	public String getNew_password2() {
		return new_password2;
	}


	/**
	 * @param new_password2 the new_password2 to set
	 */
	public void setNew_password2(String new_password2) {
		this.new_password2 = new_password2;
	}

	
}
