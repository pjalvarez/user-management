package s4c.microservices.users_management.model.services;

import java.util.ArrayList;
import java.util.HashSet;
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
}
