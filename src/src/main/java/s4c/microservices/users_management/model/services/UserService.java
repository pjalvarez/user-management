package s4c.microservices.users_management.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import s4c.microservices.users_management.model.entity.User;
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
		ArrayList<Role> toRemove = new ArrayList<Role>();
		ArrayList<Role> toAdd = new ArrayList<Role>();
		if (user.getRoles() != null) {
			for (Role rol : user.getRoles()) {
				Role r = roleRepository.findByName(rol.getName().toUpperCase());
				if (r != null) {
					r.addUser(user);
					toAdd.add(r);

				} else {
					toRemove.add(r);
					// TODO Create new role?
				}
			}
			user.getRoles().clear();
			user.setRoles(toAdd);

		}
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private User adjustAssets(User user) {
		ArrayList<Assets> toRemove = new ArrayList<Assets>();
		ArrayList<Assets> toAdd = new ArrayList<Assets>();
		if (user.getAssets() != null) {
			for (Assets asset : user.getAssets()) {
				List<Assets> r = assetsRepository.findByName(asset.getName().toUpperCase());
				
				if (r != null && !r.isEmpty()) {
					Assets as = r.get(0);					
					as.addUser(user);
					toAdd.add(as);

				} else {
					toRemove.add(asset);
					// TODO Create new Assets?
				}
			}
			user.getAssets().clear();
			user.setAssets(toAdd);

		}
		return user;
	}

	/**
	 * 
	 */
	public User postUser(User user) {
		if (user != null) {			
			user = this.adjustRoles(user);
			user = this.adjustAssets(user);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			return userRepository.saveAndFlush((user));
		
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
				for(Role role : troles){
					user.addRole(role);
				}	
			}
		}
		
		return user;
		
		
//		if (nuevos_roles != null) {
//			ArrayList<Role> newRoles = new ArrayList<Role>();
//			ArrayList<Role> toRemoveRoles = new ArrayList<Role>();
//			for (Role role_original : user.getRoles()) {
//				boolean hasRole = false;
//				for (Role role : nuevos_roles) {					
//					if (role_original.getName().toLowerCase().equals(role.getName().toLowerCase())) {
//						if(role.getDescription()!=null){
//							if(!role_original.getDescription().equals(role.getDescription())){
//								role_original.setDescription(role.getDescription());
//							}
//						}
//						hasRole = true;
//						break;
//					}
//				}
//				if (hasRole) {
//					newRoles.add(role_original);
//				} else {
//					role_original.setUsers(null);
//					toRemoveRoles.add(role_original);
//				}
//			}
//
//			for (Role role : nuevos_roles) {
//				boolean hasRole = false;
//				for (Role role_original : user.getRoles()) {
//					if (role_original.getName().toLowerCase().equals(role.getName().toLowerCase())) {
//						hasRole = true;
//						break;
//					}
//				}
//				if (!hasRole) {					
//					newRoles.add(role);
//				}
//			}
//
//			user.getRoles().removeAll(toRemoveRoles);
//			user.setRoles(newRoles);
//
//			// Delete useless entities.	
//			for(Role role : toRemoveRoles){				
//				user.getRoles().remove(role);
//				this.roleRepository.delete(role);
//			}
//
//		}		
	}
}
