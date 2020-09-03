package br.edu.ufcg.virtus.core.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.core.security.authorization.Authorization;
import br.edu.ufcg.virtus.core.service.BaseService;
import br.edu.ufcg.virtus.service.PermissionService;

/**
 * Service to be use in security features.
 * 
 * @author Virtus
 */
@Service
public class SecurityService extends BaseService {
	
	private static final String OPERATION_PATTERN = "%s-%s";
	
	/**
	 * Permission service.
	 */
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * Allows or not the user to perform the operation. 
	 * 
	 * @param instance
	 * 		Component instace.
	 * @param operation
	 * 		Operation to be performed.
	 * @return If the user can perform the operation.
	 */
	public boolean allow(Object instance, String operation) {
		return allow(false, instance, operation);
	}
	
	/**
	 * Allows or not the user to perform the operation. 
	 * 
	 * @param custom
	 * 		If it is a custom operation.
	 * @param instance
	 * 		Component instace.
	 * @param operation
	 * 		Operation to be performed.
	 * @return If the user can perform the operation.
	 */
	public boolean allow(boolean custom, Object instance, String operation) {
		String wildcard = String.format(OPERATION_PATTERN, operation.toLowerCase(), "*");
		
		if (!custom) {
			Authorization auth = AnnotationUtils.findAnnotation(instance.getClass(), Authorization.class);
			
			if (auth != null) {
				operation = String.format(OPERATION_PATTERN, operation.toLowerCase(), auth.value().toLowerCase());
			}
		}
		
		Set<String> permissions = permissionService.getPermissions(getCurrentIdRole());
		
		return permissions.contains(wildcard) || permissions.contains(operation);
	}
}
