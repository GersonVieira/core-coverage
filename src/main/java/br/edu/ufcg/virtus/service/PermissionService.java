package br.edu.ufcg.virtus.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.core.service.CrudService;
import br.edu.ufcg.virtus.model.Permission;
import br.edu.ufcg.virtus.repository.PermissionRepository;

/**
 * Service for operations with user and its permissions.
 * 
 * @author Virtus
 */
@Service
public class PermissionService extends CrudService<Permission, Integer> {

	/**
	 * Permission repository.
	 */
	@Autowired
	private PermissionRepository repository;
	
	/**
	 * (non-Javadoc)
	 * @see br.edu.ufcg.virtus.core.service.CrudService#getRepository()
	 */
	@Override
	protected PermissionRepository getRepository() {
		return repository;
	}
	
	/**
	 * Gets the permissions of the specified role.
	 * 
	 * @param idRole
	 * 		Role ID.
	 * @return The role's permissions.
	 */
	@Cacheable("role-permissions")
	public Set<String> getPermissions(Integer idRole) {
		return new HashSet<>(getRepository().findPermissionByRole(idRole));
	}
}
