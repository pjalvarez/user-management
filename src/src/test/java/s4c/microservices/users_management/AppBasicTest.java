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

}
