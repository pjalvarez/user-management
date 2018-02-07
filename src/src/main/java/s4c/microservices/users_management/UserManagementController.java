package s4c.microservices.users_management;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import s4c.microservices.users_management.model.DummieResponse;
import s4c.microservices.users_management.model.DummieRequest;
import s4c.microservices.users_management.model.entity.Assets;
import s4c.microservices.users_management.model.entity.Resource;
import s4c.microservices.users_management.model.entity.Role;
import s4c.microservices.users_management.model.entity.User;
import s4c.microservices.users_management.model.services.AssetsService;
import s4c.microservices.users_management.model.services.ResourceService;
import s4c.microservices.users_management.model.services.RoleService;
import s4c.microservices.users_management.model.services.UserService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api
@RestController
@RequestMapping("users")
public class UserManagementController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(method = RequestMethod.GET, value = "assets", produces="application/json")
	@ApiOperation(value = "getAssets", nickname = "getAssets", response = Assets.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public List<Assets> getAssets(
			@ApiParam(value = "request", required = false) @RequestBody(required = false) Assets request){
		
		return assetsService.listAssets();		

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "assets")
	@ApiOperation(value = "postAsset", nickname = "postAsset", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse postAsset(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (postAsset)");
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "assets/{assetId}")
	@ApiOperation(value = "getAssetById", nickname = "getAssetById", response = Assets.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Assets.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Assets> getAssetById (
			@PathVariable("assetId") String assetId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Assets request){
		
		Assets repository = assetsService.getAssetById(Long.parseLong(assetId));
		if (repository != null) {
			return new ResponseEntity<Assets>(repository, HttpStatus.OK);
		} else {
			return new ResponseEntity<Assets>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "assets/{assetId}")
	@ApiOperation(value = "updateAsset", nickname = "updateAsset", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse updateAsset (
			@PathVariable("assetId") String assetId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (updateAsset) " + assetId);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "assets/{assetId}")
	@ApiOperation(value = "deleteAsset", nickname = "deleteAsset", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse deleteAsset (
			@PathVariable("assetId") String assetId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (deleteAsset) " + assetId);
	}	

		
	
	@RequestMapping(method = RequestMethod.GET, value = "assets/{assetId}/check")
	@ApiOperation(value = "checkAsset", nickname = "checkAsset", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse checkAsset (
			@PathVariable("assetId") String assetId, 
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (checkAsset) " + assetId);
	}	

	
	@RequestMapping(method = RequestMethod.GET, value = "assets/{assetId}/users")
	@ApiOperation(value = "assetUser", nickname = "assetUser", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse assetUser (
			@PathVariable("assetId") String assetId, 
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (assetUser) " + assetId);
	}	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "resources")
	@ApiOperation(value = "listResources", nickname = "listResources", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public List<Resource> listResources (			 
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return resourceService.listResources();
	}	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "roles")
	@ApiOperation(value = "getRoles", nickname = "getRoles", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public List<Role> getRoles(
			@ApiParam(value = "request", required = false) @RequestBody(required = false) Role request){
		
		return roleService.listRoles();
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "roles")
	@ApiOperation(value = "postRole", nickname = "postRole", response = Role.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<?> postRole(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) Role request) {

		if  (roleService.getRoleByName(request.getName()) == null){
			if(roleService.isResourcesOk(request)){
				Role role = roleService.postRole(request);
				if (role != null) {
					return new ResponseEntity<Role>(role, HttpStatus.CREATED);
				} 
				return new ResponseEntity<Role>(HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				return new ResponseEntity<String>("Some of the resources doesnt exist",HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			return new ResponseEntity<String>("Role previously registered",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "roles/user/{userId}")
	@ApiOperation(value = "getRolesUser", nickname = "getRolesUser", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<List<Role>> getRolesUser(
			@PathVariable("userId") String userId){
		
		User user = userService.getUserById(Long.parseLong(userId));
		if(user != null){
			return new ResponseEntity(user.getRoles(),HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "roles/user/{userId}")
	@ApiOperation(value = "addRolesToUser", nickname = "addRolesToUser", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<String> addRolesToUser(
			@PathVariable("userId") String userId,		
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) List<Role> request){
			
			User user = userService.getUserById(Long.parseLong(userId));
			if(user != null){
				boolean success = userService.updateUserRoles(user,request);
				if(success){
					return new ResponseEntity("",HttpStatus.OK);	
				} else {
					return new ResponseEntity("Update Failure. Roles must exist and not be related to User.",HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
			} else {
				return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
			}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "roles/user/{userId}")
	@ApiOperation(value = "deleteRolesFromUser", nickname = "deleteRolesFromUser", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity deleteRolesFromUser(
			@PathVariable("userId") String userId,		
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = true) List<Role> request){
		
			User user = userService.getUserById(Long.parseLong(userId));
			if(user != null){
				boolean success = userService.deleteUserRoles(user,request);
				if(success){
					return new ResponseEntity("",HttpStatus.OK);	
				} else {
					return new ResponseEntity("Delete failed. Roles must be related to User.",HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
			} else {
				return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
			}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "roles/user/{userId}/hasRole/{roleId}")
	@ApiOperation(value = "checkRoleUser", nickname = "checkRoleUser", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<String> checkRoleUser(
			@PathVariable("userId") String userId,
			@PathVariable("roleId") String roleId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) String request){
		
		    Role role = roleService.getRoleById(Long.parseLong(roleId));
		    User user = userService.getUserById(Long.parseLong(userId));
		    
		    if((role != null) && (user != null)){
		    	if(user.getRoles().contains(role)){
		    		return new ResponseEntity<String>("User has Role",HttpStatus.OK);	
		    	} else {
		    		return new ResponseEntity<String>("User does not have Role",HttpStatus.CREATED);
		    	}
		    } else {
		    	return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
		    }
	}
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "roles/{roleId}")
	@ApiOperation(value = "getRoleById", nickname = "getRoleById", response = Role.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Role.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Role> getRoleById (
			@PathVariable("roleId") String roleId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Role request){
		
			Role repository  = roleService.getRoleById(Long.parseLong(roleId));
			if (repository != null) {
				return new ResponseEntity<Role>(repository, HttpStatus.OK);
			} else {
				return new ResponseEntity<Role>(HttpStatus.UNPROCESSABLE_ENTITY);
			}		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "roles/{roleId}")
	@ApiOperation(value = "updateRole", nickname = "updateRole", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<String> updateRole (
			@PathVariable("roleId") String roleId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) Role request){
		
		Role role = roleService.getRoleById(Long.parseLong(roleId));		
		
		if(role != null){
			if(request != null){
				boolean result = roleService.updateRole(role,request);
				if(result){
					return new ResponseEntity<String>("",HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Failure updating Role",HttpStatus.UNPROCESSABLE_ENTITY);
				}				
			} else {
				return new ResponseEntity<String>("Empty body",HttpStatus.NO_CONTENT);	
			}
			
		} else {
			return new ResponseEntity<String>("Role not found",HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "roles/{roleId}")
	@ApiOperation(value = "deleteRole", nickname = "deleteRole", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<Role> deleteRole (
			@PathVariable("roleId") String roleId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) Role request){
		
		Boolean result = this.roleService.deleteRole(Long.parseLong(roleId));
		if(result){
			return new ResponseEntity<Role>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Role>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "roles/{roleId}/resources")
	@ApiOperation(value = "getResourcesRoles", nickname = "getResourcesRoles", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<?> getResourcesRoles (
			@PathVariable("roleId") String roleId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		
		Role role = roleService.getRoleById(Long.parseLong(roleId));
		if(role != null){
			return new ResponseEntity<List<Resource>>(role.getResources(),HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Role not found",HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "roles/{roleId}/resources")
	@ApiOperation(value = "postResourcesRoles", nickname = "postResourcesRoles", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<String> postResourcesRoles (
			@PathVariable("roleId") String roleId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) List<Resource> request){
		
		Role role = roleService.getRoleById(Long.parseLong(roleId));		
		
		if(role != null){
			if(request != null){
				boolean result = roleService.addResources(role,request);
				if(result){
					return new ResponseEntity<String>("",HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Failure registering resources",HttpStatus.UNPROCESSABLE_ENTITY);
				}				
			} else {
				return new ResponseEntity<String>("Nothing to do",HttpStatus.NO_CONTENT);	
			}
			
		} else {
			return new ResponseEntity<String>("Role not found",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}

	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "roles/{roleId}/resources/{resourceId}")
	@ApiOperation(value = "removeResourceRole", nickname = "removeResourceRole", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity removeResourceRole (
			@PathVariable("roleId") String roleId,
			@PathVariable("resourceId") String resourceId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		
		Role role = roleService.getRoleById(Long.parseLong(roleId));
		Resource resource = resourceService.getResourceById(Long.parseLong(resourceId));
		if((resource != null) && (role != null)) {
			if(role.getResources().contains(resource)){
				roleService.removeResource(role, resource);
				return new ResponseEntity<String>(HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Resource must be related to Role",HttpStatus.UNPROCESSABLE_ENTITY);	
			}
		} else {
			return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "roles/{roleId}/user")
	@ApiOperation(value = "getUsersRole", nickname = "getUsersRole", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<List<User>> getUsersRole (
			@PathVariable("roleId") String roleId){
		
		Role role = roleService.getRoleById(Long.parseLong(roleId));
		if(role != null){			
			return new ResponseEntity<List<User>>(role.getUsers(),HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "sessions")
	@ApiOperation(value = "getSessions", nickname = "getSessions", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse getSessions (			
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (getSessions) ");
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "users")
	@ApiOperation(value = "getUsers", nickname = "getUsers", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public List<User> getUsers(
			@ApiParam(value = "request", required = false) @RequestBody(required = false) User request){
		
		return userService.listUsers();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "users")
	@ApiOperation(value = "postUser", nickname = "postUser", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public ResponseEntity<?> postUser(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) User request){
		
			if(userService.findByEmail(request.getEmail()) == null){
				User repository = userService.postUser(request);
				if(repository != null) {
					return new ResponseEntity<User>( repository,HttpStatus.CREATED);
				} else {
					return new ResponseEntity<User>(HttpStatus.UNPROCESSABLE_ENTITY);
				}
			} else {				
				return new ResponseEntity<String>("Previously registered email",HttpStatus.CONFLICT);
			}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "users/login")
	@ApiOperation(value = "login", nickname = "login", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse login(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (login)");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "users/logout")
	@ApiOperation(value = "logout", nickname = "logout", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse logout(
			@ApiParam(value = "request", required = false) @RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (logout)");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "users/me")
	@ApiOperation(value = "getUserMe", nickname = "getUserMe", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse getUserMe(
			@ApiParam(value = "request", required = false) @RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (getUserMe)");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "users/reset")
	@ApiOperation(value = "resetPasswordEmail", nickname = "resetPasswordEmail", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse resetPasswordEmail(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (resetPasswordEmail)");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "users/reset/token")
	@ApiOperation(value = "resetPassword", nickname = "resetPassword", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse resetPassword(
			@ApiParam(value = "request", required = true) @RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (resetPassword)");
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "users/verify/{token}")
	@ApiOperation(value = "verifyUser", nickname = "verifyUser", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse verifyUser(
			@PathVariable("token") String token,
			@ApiParam(value = "request", required = false) @RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (verifyUser) " + token);
	}
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "users/{userId}")
	@ApiOperation(value = "getUserById", nickname = "getUserById", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId,
			@ApiParam(value = "request", required = false) @RequestBody(required = false) User request) {

		User repository = userService.getUserById(Long.parseLong(userId));
		if (repository != null) {
			return new ResponseEntity<User>(repository, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "users/{userId}")
	@ApiOperation(value = "updateUser", nickname = "updateUser", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse updateUser (
			@PathVariable("userId") String userId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (updateUser) " + userId);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "users/{userId}")
	@ApiOperation(value = "deleteUser", nickname = "deleteUser", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse deleteUser (
			@PathVariable("userId") String userId,
			@ApiParam(value = "request", required = false) 
			@RequestBody(required = false) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (deleteUser) " + userId);
	}	

	
	@RequestMapping(method = RequestMethod.PUT, value = "users/{userId}/password")
	@ApiOperation(value = "changePassword", nickname = "changePassword", response = DummieResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })	
	public DummieResponse changePassword (
			@PathVariable("userId") String userId,
			@ApiParam(value = "request", required = true) 
			@RequestBody(required = true) DummieRequest request){
		
		return new DummieResponse("S4C. Not yet implemented (changePassword) " + userId);
	}
	
	
	
}