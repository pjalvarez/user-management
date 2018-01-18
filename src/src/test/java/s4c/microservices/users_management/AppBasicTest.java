package s4c.microservices.users_management;


import java.util.HashMap;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import s4c.microservices.users_management.model.DummieResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppBasicTest 
{
    
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void postAssetsTest() {
		String url = "/users/assets";
		String json = "{\"id\":\"666\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<DummieResponse> response = this.restTemplate.postForEntity(url,
				request, DummieResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void getAssetByIdTest() {
		String url = "/users/assets/{id}";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");		 
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				 DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void updateAssetTest() {
		String url = "/users/assets/{id}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deleteAssetTest() {
		String url = "/users/assets/{id}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
				null, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void checkAssetsTest() {
		String url = "/users/assets/{id}/check";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");	
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void assetsUserTest() {
		String url = "/users/assets/{id}/users";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");	
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}	
	@Test
	public void listResourcesTest() {
		String url = "/users/resources";
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void getRolesTest() {
		String url = "/users/roles";
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void postRoleTest() {
		String url = "/users/roles";
		String json = "{\"id\":\"666\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<DummieResponse> response = this.restTemplate.postForEntity(url,
				request, DummieResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	
	@Test
	public void getRolesUserTest() {
		String url = "/users/roles/user/{id}";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");		 
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				 DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void addRolesToUserTest() {
		String url = "/users/roles/user/{id}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deleteRolesFromUserTest() {
		String url = "/users/roles/user/{id}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
				null, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void checkRoleUserTest() {
		String url = "/users/roles/user/{id}/hasRole/{roleId}";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");
		 urlVariables.put("roleId", "44");		 
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				 DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	
	@Test
	public void getRolesByIdTest() {
		String url = "/users/roles/{id}";
		 HashMap<String, String> urlVariables = new HashMap<String, String>();
		 urlVariables.put("id", "44");		 
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				 DummieResponse.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void addRolesTest() {
		String url = "/users/roles/{id}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deleteRolesTest() {
		String url = "/users/roles/{id}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
				null, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	
	@Test
	public void getResourcesRoleTest() {
		String url = "/users/roles/{id}/resources";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");	
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void postResourcesRoleTest() {
		String url = "/users/roles/{id}/resources";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void removeResourceRoleTest() {
		String url = "/users/roles/{id}/resources/{resourceId}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");
		urlVariables.put("resourceId", "44");
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
				null, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}	

	@Test
	public void getSessionsTest() {
		String url = "/users/sessions";
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	

	@Test
	public void userLogoutTest() {
		String url = "/users/users/logout";
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void getUserMeTest() {
		String url = "/users/users/me";
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void resetPasswordEmailTest() {
		String url = "/users/users/reset";
		String json = "{\"id\":\"666\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<DummieResponse> response = this.restTemplate.postForEntity(url,
				request, DummieResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void resetPasswordlTest() {
		String url = "/users/users/reset/token";
		String json = "{\"id\":\"666\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<DummieResponse> response = this.restTemplate.postForEntity(url,
				request, DummieResponse.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void verifyUserTest() {
		String url = "/users/users/verify/{token}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("token", "44");	
		ResponseEntity<DummieResponse> response = this.restTemplate.getForEntity(url,
				DummieResponse.class, String.class,urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	

	@Test
	public void updateUserTest() {
		String url = "/users/users/{id}";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deleteUserTest() {
		String url = "/users/users/{id}";;
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
				null, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void changePasswordTest() {
		String url = "/users/users/{id}/password";
		HashMap<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "44");

		String json = "{\"nombre\":\"Fede\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
				request, String.class, urlVariables);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	

}
