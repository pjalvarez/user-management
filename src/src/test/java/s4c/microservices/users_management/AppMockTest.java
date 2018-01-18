package s4c.microservices.users_management;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import s4c.microservices.users_management.model.DummieResponse;
import s4c.microservices.users_management.model.entity.User;
import s4c.microservices.users_management.model.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AppMockTest 
{
	
	static final String user_json = "{\"name\": \"User 1\",\"surname\": \"Example\",\"email\": \"email@example.com\",\"date_of_birth\": \"1990-02-26\",\"password\": \"dm1m2'%cma1g64$4&1\",\"gender\": \"female\",\"assets\": [{\"name\":\"Asset A\"}],\"roles\": [{\"name\":\"Administrador\"}]}";
	
    private MockMvc mockMvc;
    private List<User> userList = new ArrayList<>();
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
    }
    
    @Before
    public void setup() throws Exception {    	
        this.mockMvc = webAppContextSetup(webApplicationContext).addFilter(this.springSecurityFilterChain).build();
        

        
        this.userRepository.deleteAllInBatch();
        
        try {
	        this.userList.add(userRepository.save(new User(1L,"rcarballo",bCryptPasswordEncoder.encode("Emergya"),"rcarballo@emergya.com","Carballo")));
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
		mockMvc.perform(post(url)
				.content(user_json)
				.contentType(contentType)
				).andExpect(status().isOk());
	}
	
	@Test
	public void userLoginTest() throws Exception {

		String contenido = this.obtainAccessToken("rcarballo", "Emergya");
		Assert.assertFalse(StringUtils.isEmpty(contenido));
	}
	

	@Test
	public void getUserByIdTest() throws Exception {
		String url = "/users/users/" + this.userList.get(0).getId();
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}

	

}
