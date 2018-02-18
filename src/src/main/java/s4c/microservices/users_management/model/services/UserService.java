package s4c.microservices.users_management.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import s4c.microservices.users_management.model.entity.User;
import s4c.microservices.users_management.model.changePasswordRequest;
import s4c.microservices.users_management.model.entity.Assets;
import s4c.microservices.users_management.model.entity.Role;
import s4c.microservices.users_management.model.repository.AssetsRepository;
import s4c.microservices.users_management.model.repository.RoleRepository;
import s4c.microservices.users_management.model.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AssetsRepository assetsRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Returns a complete list of Users
	 */
	@Override
	public List<User> listUsers() {

		return userRepository.findAll();

	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private User adjustRoles(User user) {		
		ArrayList<Role> toAdd = new ArrayList<Role>();
		if (user.getRoles() != null) {
			boolean ok = true;
			for (Role rol : user.getRoles()) {
				Role r = roleRepository.findOne(rol.getId());
				if (r != null) {
					r.addUser(user);
					toAdd.add(r);

				} else {
					ok = false;
					break;
					
				}
			}
			if(ok){
				user.getRoles().clear();
				user.setRoles(toAdd);
			} else {
				user = null;
			}

		}
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private User adjustAssets(User user) {		
		ArrayList<Assets> toAdd = new ArrayList<Assets>();
		if (user.getAssets() != null) {
			boolean ok = true;
			for (Assets asset : user.getAssets()) {
				Assets r = assetsRepository.findOne(asset.getId());				
				if (r != null){										
					r.addUser(user);
					toAdd.add(r);

				} else {
					ok = false;							
					break;
				}
			}
			if(ok){
				user.getAssets().clear();
				user.setAssets(toAdd);
			} else {
				user = null;
			}

		}
		return user;
	}

	/**
	 * 
	 */
	public User postUser(User user) {
		if (user != null) {			
			user = this.adjustRoles(user);			
			if(user != null){				
				user = this.adjustAssets(user);
			}
			if (user !=null){				
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				return userRepository.saveAndFlush((user));
			}
		
		}
		return null;
	}

	public User getUserById(Long userId) {
		return userRepository.findOne(userId);

	}
	
    
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    
    /**
     * Add to user previously registered roles.
     * @param user
     * @param nuevos_roles
     * @return
     */
	public boolean updateUserRoles(User user, List<Role> nuevos_roles) {
		
		if(nuevos_roles!=null){
			user =  this.getRideOfUserRolesUpdate(user, nuevos_roles);
			if(user != null) {
				userRepository.saveAndFlush(user);
				return true;
			} else 
				return false;
		}
		
		return false;
	}
	
	public boolean deleteUserRoles(User user, List<Role> nuevos_roles) {
		
		if(nuevos_roles!=null){
			user =  this.getRideOfUserRolesDelete(user, nuevos_roles);
			if(user != null) {
				userRepository.saveAndFlush(user);
				return true;
			} else 
				return false;
		}
		
		return false;
	}

	
	/**
	 *  
	 * @param user
	 * @param nuevos_roles
	 * @return
	 */
	private User getRideOfUserRolesDelete(User user, List<Role> nuevos_roles) {
		if (nuevos_roles != null) {
			ArrayList<Role> troles = new ArrayList<Role>();
			boolean ok = true;
			//check if all roles exists
			for(Role role : nuevos_roles){
				try {
					Role orole = this.roleRepository.findOne(role.getId());
					if((orole.getId()!=null) && (user.getRoles().contains(orole))){
							troles.add(orole);	
						
					} else {
						ok = false;
						break;
					}
				} catch (javax.persistence.EntityNotFoundException e){
					ok = false;
					break;	
				}
			}
			
			if(!ok){
				user = null;
			} else {
				if(!troles.isEmpty()){
					for(Role role : troles){					
						user.removeRole(role);
					}
				}
			}
		}
		
		return user;
	}
	
	/**
	 *  
	 * @param user
	 * @param nuevos_roles
	 * @return
	 */
	private User getRideOfUserRolesUpdate(User user, List<Role> nuevos_roles) {
		if (nuevos_roles != null) {
			ArrayList<Role> troles = new ArrayList<Role>();
			boolean ok = true;
			//check if all roles exists
			for(Role role : nuevos_roles){
				try {
					Role orole = this.roleRepository.findOne(role.getId());
					if((orole!=null)){
						//Avoids add same Role serveral times. 
						if(!user.getRoles().contains(orole))
							troles.add(orole);					
					} else {
						ok = false;
						break;
					}
				} catch (javax.persistence.EntityNotFoundException e){
					ok = false;
					break;	
				}
			}
			
			if(!ok){
				user = null;
			} else {
			//we have to delete roles doesnt exist in the new set
		    ArrayList<Role> toRemove = new ArrayList<Role>();
			for(Role role : user.getRoles()){
				boolean found = false;
				for(Role nrole : nuevos_roles){
					if(role.getId().equals(nrole.getId())){
						found = true;
						break;
					}
				}
				if(!found){
					toRemove.add(role);
				}
			}
			
			for(Role role: toRemove){
				user.removeRole(role);
			}
				
				for(Role role : troles){
					user.addRole(role);
				}	
			}
		}		
		return user;		
	}

	
	/**
	 *  
	 * @param user
	 * @param nuevos_assets
	 * @return
	 */
	private User getRideOfUserAssetsUpdate(User user, List<Assets> nuevos_assets) {
		if (nuevos_assets != null) {
			ArrayList<Assets> tassets = new ArrayList<Assets>();
			boolean ok = true;
			//check if all roles exists
			for(Assets asset : nuevos_assets){
				try {
					Assets oasset = this.assetsRepository.findOne(asset.getId());
					if((oasset!=null)){
						//Avoids add same Role serveral times. 
						if(!user.getAssets().contains(oasset))
							tassets.add(oasset);					
					} else {
						ok = false;
						break;
					}
				} catch (javax.persistence.EntityNotFoundException e){
					ok = false;
					break;	
				}
			}
			
			if(!ok){
				user = null;
			} else {
				
				//we have to delete assets doesnt exist in the new set
				ArrayList<Assets> toRemove = new ArrayList<Assets>();
				for(Assets asset : user.getAssets()){
					boolean found = false;
					for(Assets nasset : nuevos_assets){
						if(asset.getId().equals(nasset.getId())){
							found = true;
							break;
						}
					}
					if(!found){
						toRemove.add(asset);
					}
				}

				
				for(Assets asset : toRemove){
					user.removeAsset(asset);
				}
				for(Assets asset : tassets){
					user.addAssets(asset);
				}	
			}
		}		
		return user;		
	}

	
	public void deleteUser(User user) {
		userRepository.delete(user);		
	}

	public User updateUser(User user, User request) {
		if( user.getDate_of_birth() == null || (request.getDate_of_birth()!=null && !user.getDate_of_birth().equals(request.getDate_of_birth())))
			user.setDate_of_birth(request.getDate_of_birth());
		if(user.getEmail()== null || (request.getEmail()!=null && !user.getEmail().equals(request.getEmail())))
			user.setEmail(request.getEmail());
		if(user.getGender()==null ||(request.getGender()!=null && !user.getGender().equals(request.getGender())))
			user.setGender(request.getGender());
		if(user.getName() == null || (request.getName()!=null && !user.getName().equals(request.getName())))
			user.setName(request.getName());
		if(user.getUsername()== null || (request.getUsername()!=null && !user.getUsername().equals(request.getUsername())))
			user.setUsername(request.getUsername());
		if(user.getUsername()== null || (request.getUsername()!=null && !user.getUsername().equals(request.getUsername())))
			user.setUsername(request.getUsername());
		if(user.getSurname()== null ||(request.getSurname()!=null && !user.getSurname().equals(request.getSurname())))
			user.setSurname(request.getSurname());
		if(user.getPassword()==null || (request.getPassword()!=null && !user.getPassword().equals(bCryptPasswordEncoder.encode(request.getPassword()))))
			user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		
		user = this.getRideOfUserRolesUpdate(user, request.getRoles());
		if(user != null){
			user = this.getRideOfUserAssetsUpdate(user, request.getAssets());	
		}
		if(user != null){
			userRepository.saveAndFlush(user);
		}
			
		return user;
		
	}

	public User changePassword(User user, changePasswordRequest request) {
		if(bCryptPasswordEncoder.matches(request.getOld_password(),user.getPassword()))
			if(request.getNew_password1().equals(request.getNew_password2())){
				user.setPassword(bCryptPasswordEncoder.encode(request.getNew_password1()));
				return  userRepository.saveAndFlush(user);				
			}
		
		return null;
	}
}
