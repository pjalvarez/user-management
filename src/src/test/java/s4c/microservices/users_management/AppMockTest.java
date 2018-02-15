package s4c.microservices.users_management;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import s4c.microservices.users_management.model.entity.Assets;
import s4c.microservices.users_management.model.entity.Resource;
import s4c.microservices.users_management.model.entity.Role;
import s4c.microservices.users_management.model.entity.User;
import s4c.microservices.users_management.model.repository.AssetsRepository;
import s4c.microservices.users_management.model.repository.ResourceRepository;
import s4c.microservices.users_management.model.repository.RoleRepository;
import s4c.microservices.users_management.model.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AppMockTest 
{
	
	static final String user_json = "{\"name\": \"User 1\",\"surname\": \"Example\",\"email\": \"email@example.com\",\"date_of_birth\": \"1990-02-26\",\"password\": \"dm1m2'%cma1g64$4&1\",\"gender\": \"female\",\"assets\": [{\"name\":\"Asset A\"}],\"roles\": [{\"name\":\"Administrador\"}]}";
	static final String role_new_json = "{ \"name\": \"Get Devices\",\"description\": \"This resource allows to get the devices information\",\"resources\": []}";
	static final String role_complete_new_json = "{ \"name\": \"Get Devices\",\"description\": \"This resource allows to get the devices information\",\"resources\": [{\"name\": \"GET_Device_DeviceId\", \"value\":false},{\"name\": \"GET_Devices_Unexistent\", \"value\":true},{\"name\": \"METHOD_Resource\", \"value\":false}]}";
	static final String role_complete_update_json = "{ \"name\": \"Get Devices\",\"description\": \"This resource allows to get the devices information\",\"resources\": [{\"name\": \"GET_Device_DeviceId44\", \"value\":false},{\"name\": \"GET_Devices_Unexistent\", \"value\":true},{\"name\": \"METHOD_Resource\", \"value\":false}]}";
	static final String role_resources_new_json = "[{\"name\":\"Role A\",\"description\":\"none\"}]";
	static final String update_user_roles_json = "[{\"id\":\"3\"}]";
	
    private MockMvc mockMvc;
    private List<User> userList = new ArrayList<>();
    private List<Resource> resourcesList = new ArrayList<>();
    private List<Role> roleList = new ArrayList<>();
    private List<Assets> assetsList = new ArrayList<>();
    private HttpMessageConverter<?> mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
    
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private AssetsRepository assetsRepository;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;
	
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    
    private String obtainAccessToken(String username, String password) throws Exception {
    	  
    	

    	String authChain = clientId + ":" + clientSecret;
    	String authorization = "Basic " + new String(Base64Utils.encode(authChain.getBytes()));
		String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

		String content = mockMvc
				.perform(
						post("/users/login")
								.header("Authorization", authorization)
								.contentType(contentType)
								.param("grant_type", "password")
								.param("username", username)
								.param("password", password)
//								.param("scopes", "read write")
								)
				.andExpect(status().isOk())			
				.andReturn().getResponse().getContentAsString();
		
		return content;
    }
    
    @After
    public void ends(){
    	this.userRepository.deleteAll();
    	this.roleRepository.deleteAll();
    	this.resourceRepository.deleteAll();
    	this.assetsRepository.deleteAll();
    }
    
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = webAppContextSetup(webApplicationContext).addFilter(this.springSecurityFilterChain).build();
        
        this.userRepository.deleteAllInBatch();
        this.roleRepository.deleteAllInBatch();
        this.resourceRepository.deleteAllInBatch();
        this.assetsRepository.deleteAllInBatch();
        
        
        try {
        	
        	
        	this.assetsList.add(assetsRepository.save(new Assets(1L,"Asset A","yy","Building")));
        	this.assetsList.add(assetsRepository.save(new Assets(2L,"Asset B","xx","Building")));
        	this.assetsList.add(assetsRepository.save(new Assets(3L,"Asset MM","xe4eeex","Building")));
        	this.assetsList.add(assetsRepository.save(new Assets(4L,"Asset 4444444","xe4eeex","Building")));
        	
        	this.roleList.add(roleRepository.save(new Role(1L,"Role A","none")));
        	this.roleList.add(roleRepository.save(new Role(2L,"Role To Delete","none")));
        	this.roleList.add(roleRepository.save(new Role(3L,"Role To Add to user","none")));
        	this.resourcesList.add(resourceRepository.saveAndFlush(new Resource(1L,"Resource A","none")));
        	
        	
        	User usuarioA = new User("rcarballo",bCryptPasswordEncoder.encode("Emergya"),"Carballo","rcarballo@emergya.com");
        	//usuarioA.addAssets(assetsList.get(0));
        	//usuarioA.addRole(roleList.get(0));
	        this.userList.add(userRepository.save(usuarioA));
	        this.userList.add(userRepository.save(new User("rcarballo75",bCryptPasswordEncoder.encode("Emergya"),"Carballo","rcarballo75@emergya.com")));
        } catch (DataIntegrityViolationException e){
        	this.userList.add(userRepository.findOne(1L));
        	
        }
        
    }
	

	@Test
	public void getAssetsTest() throws Exception {
		String url = "/users/assets";
		mockMvc.perform(get(url)).andExpect(status().isOk());

	}
	
	@Test
	public void getUsersTest() throws Exception {
		String url = "/users/users";
		mockMvc.perform(get(url)).andExpect(status().isOk());

	}

	@Test
	public void postUsersTest() throws Exception {
		String url = "/users/users";
		 String json = "{\"name\": \"User 1\",\"surname\": \"Example\",\"email\": \"email@example.com\",\"date_of_birth\": \"1990-02-26\",\"password\": \"dm1m2'%cma1g64$4&1\",\"gender\": \"female\",\"assets\": [{\"id\":\""+this.assetsList.get(0).getId()+"\"}],\"roles\": [{\"id\":\""+this.roleList.get(0).getId()+"\"}]}";
		mockMvc.perform(post(url)
				.content(json)
				.contentType(contentType)
				).andExpect(status().isCreated());
	}
	
	@Test
	public void userLoginTest() throws Exception {

		String contenido = this.obtainAccessToken("rcarballo75@emergya.com", "Emergya");
		Assert.assertFalse(StringUtils.isEmpty(contenido));
	}
	

	@Test
	public void getUserByIdTest() throws Exception {
		String url = "/users/users/" + this.userList.get(0).getId();
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}
	@Test
	public void getResourcesTest() throws Exception {
		String url = "/users/resources";
		mockMvc.perform(get(url)).andExpect(status().isOk());

	}
	@Test
	public void postRoleTest() throws Exception {
		String url = "/users/roles";
		mockMvc.perform(post(url)
				.content(role_new_json)
				.contentType(contentType)
				).andExpect(status().isCreated());
	}		
	@Test
	public void postRoleFailureTest() throws Exception {
		String url = "/users/roles";
		mockMvc.perform(post(url)
				.content(role_complete_new_json)
				.contentType(contentType)
				).andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void postResourcesRolesTest() throws Exception {
		String url = "/users/roles/" + roleList.get(0).getId() + "/resources";
		mockMvc.perform(put(url)
				.content(role_resources_new_json)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}
	
	@Test
	public void getRolesTest() throws Exception {
		String url = "/users/roles";
		mockMvc.perform(get(url)).andExpect(status().isOk());

	}
	@Test
	public void getRoleByIdTest() throws Exception {
		String url = "/users/roles/"+this.roleList.get(0).getId();
		mockMvc.perform(get(url)).andExpect(status().isOk());
		
	}
	
	@Test
	public void updateRoleTest() throws Exception {
		String url = "/users/roles/" + roleList.get(0).getId();
		mockMvc.perform(put(url)
				.content(role_complete_update_json)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}
	
	@Test
	public void deleteRoleTest() throws Exception {		
		String url = "/users/roles/" + roleList.get(1).getId();
		
		mockMvc.perform(delete(url)				
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void getRolesUserTest() throws Exception {		
		String url = "/users/roles/user/" + this.userList.get(0).getId();		
		mockMvc.perform(get(url)				
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void updateUserRolesTest() throws Exception {
		String url = "/users/roles/user/" + this.userList.get(0).getId();
		String update_user_roles_json = "[{\"id\":\""+this.roleList.get(2).getId()+"\"}]";
		mockMvc.perform(put(url)
				.content(update_user_roles_json)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}
	
	@Test
	public void deleteUserRolesTest() throws Exception {

		//Set relation
		String url = "/users/roles/user/" + this.userList.get(0).getId();
		String update_user_roles_json = "[{\"id\":\""+this.roleList.get(2).getId()+"\"}]";
		mockMvc.perform(put(url)
				.content(update_user_roles_json)
				.contentType(contentType)
				).andExpect(status().isOk());	
		
		//Delete relation
		url = "/users/roles/user/" + this.userList.get(0).getId();		
		mockMvc.perform(delete(url)
				.content(update_user_roles_json)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}	
	
	@Test
	public void getUsersRoleTest() throws Exception {
		String url = "/users/roles/" + this.roleList.get(0).getId()+"/user";		
		mockMvc.perform(get(url)				
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void checkRoleUserTest() throws Exception {
		String url = "/users/roles/user/" + this.userList.get(0).getId() + "/hasRole/" + this.roleList.get(0).getId();		
		mockMvc.perform(get(url)				
				.contentType(contentType)
				).andExpect((status().is2xxSuccessful()));
	}	
	
	
	@Test
	public void getResourcesRolesTest() throws Exception {
		String url = "/users/roles/" + this.roleList.get(0).getId() + "/resources";
		mockMvc.perform(get(url)				
				.contentType(contentType)
				).andExpect((status().isOk()));
		
	}
	
	@Test
	public void removeResourceRoleTest() throws Exception {
		
		Role role =this.roleList.get(0);
		Resource resource = this.resourcesList.get(0);
		role.addResource(resource);
		this.roleRepository.saveAndFlush(role);
		
		String url = "/users/roles/" + role.getId() + "/resources/" + resource.getId();
		mockMvc.perform(delete(url)				
				.contentType(contentType)
				).andExpect((status().isOk()));
	}
	
	@Test
	public void postAssetTest() throws Exception {
		String url = "/users/assets";
		String json = "{\"name\": \"Asset JJJ\",\"description\": \"none\",\"type\": \"Building\",\"parents\": [{\"id\":\""+assetsList.get(0).getId()+"\"}],\"childrens\": [{\"id\":\""+assetsList.get(1).getId()+"\"}]}";
		mockMvc.perform(post(url)
				.content(json)
				.contentType(contentType)
				).andExpect(status().isCreated());
	}
	
	@Test
	public void getAssetByIdTest() throws Exception {
		String url = "/users/assets/" + this.assetsList.get(0).getId();
		mockMvc.perform(get(url)				
				.contentType(contentType)
				).andExpect((status().isOk()));
		
	}
	
	@Test
	public void updateAssetTest() throws Exception {
		String url = "/users/assets/" + this.assetsList.get(3).getId();
		String json = "{\"name\": \"Asset CUCU\",\"description\": \"none\",\"type\": \"Building\",\"parents\": [{\"id\":\""+assetsList.get(2).getId()+"\"}],\"childrens\": [{\"id\":\""+assetsList.get(2).getId()+"\"}]}";
		mockMvc.perform(put(url)
				.content(json)
				.contentType(contentType)
				).andExpect(status().isOk());	
	}
	
	@Test
	public void deleteAssetTest() throws Exception {
		String url = "/users/assets/" + this.assetsList.get(3).getId();
		mockMvc.perform(delete(url)
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void checkAsset() throws Exception {
		String url = "/users/assets/" + this.assetsList.get(3).getId()+"/check";		
		mockMvc.perform(get(url)
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void checkAssetFailure() throws Exception {
		String url = "/users/assets/900000000/check";		
		mockMvc.perform(get(url)
				.contentType(contentType)
				).andExpect(status().isUnprocessableEntity());
	}	
	
	@Test
	public void assetUserTest() throws Exception {
		String url = "/users/assets/" + this.assetsList.get(3).getId()+"/users";		
		mockMvc.perform(get(url)
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void deleteUserTest() throws Exception {
		String url = "/users/users/" + this.userList.get(1).getId();
		mockMvc.perform(delete(url)
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void updateUserTest() throws Exception {
		String url = "/users/users/" + this.userList.get(0).getId();
		String json = "{\"name\": \"User 1\",\"surname\": \"Example\",\"email\": \"email@example.com\",\"date_of_birth\": \"1990-02-26\",\"password\": \"dm1m2'%cma1g64$4&1\",\"gender\": \"female\",\"assets\": [{\"id\":\""+this.assetsList.get(0).getId()+"\"}],\"roles\": []}";
		mockMvc.perform(put(url)
				.content(json)
				.contentType(contentType)
				).andExpect(status().isOk());
		
	}
	
	@Test
	public void changePasswordTest() throws Exception {
		String url = "/users/users/" + this.userList.get(0).getId() + "/password";
		String json ="{\"old_password\" :\"Emergya\",\"new_password1\" :\"cucux\",\"new_password2\" :\"cucux\"}";
		mockMvc.perform(put(url)
				.content(json)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}
	
	@Test
	public void changePasswordFailTest() throws Exception {
		String url = "/users/users/" + this.userList.get(0).getId() + "/password";
		String json ="{\"old_password\" :\"EmergyaX\",\"new_password1\" :\"cucux\",\"new_password2\" :\"cucux\"}";
		mockMvc.perform(put(url)
				.content(json)
				.contentType(contentType)
				).andExpect(status().isUnprocessableEntity());		
	}
	
	@Test
<<<<<<< HEAD
	public void addSessions() throws Exception {
		String url = "/users/sessions/";
		String json ="{\"ip\" :\"35.228.48.125\",\"user_id\" :\"507f191e810c19729de860ef\",\"user_agent\" :\"Chrome 38.4\",\"created_at\" :\"2018-02-13\"}";
		
		mockMvc.perform(post(url)
				.content(json)
				.contentType(contentType)
				).andExpect(status().isCreated());	
	}
	
	@Test
	public void getSessionsTest() throws Exception {
		String url = "/users/sessions";
		mockMvc.perform(get(url)).andExpect(status().isOk());

	}
=======
	public void getAssetByUserIdTest() throws Exception {
		String url = "/users/assets/" + this.userList.get(0).getId() + "/user";
		
		mockMvc.perform(get(url)
				.contentType(contentType)
				).andExpect(status().isOk());		
	}
	
	
>>>>>>> 489b2fc5e3fa0b6de3535041b32ef2724e1d1f14

}
