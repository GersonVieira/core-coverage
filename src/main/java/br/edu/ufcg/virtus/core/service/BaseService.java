package br.edu.ufcg.virtus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ufcg.virtus.core.dto.LoggedUserDTO;

/**
 * The 'BaseService' class provides the common API for all services.
 * <p>
 * All services MUST extend this class.
 *
 * @author Virtus
 */
public abstract class BaseService {

    /**
     * Logger.
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Gets the current user.
     * 
     * @return Current user.
     */
    protected LoggedUserDTO getCurrentUser() {
        return (LoggedUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    /**
     * Gets the current user username.
     * 
     * @return Username.
     */
    protected String getCurrentUsername() {
    	return getCurrentUser().getUsername();
    }
    
    /**
     * Gets the current user ID.
     * 
     * @return ID.
     */
    protected Integer getCurrentUserId() {
    	return getCurrentUser().getId();
    }
    
    /**
     * Gets the current role ID.
     * 
     * @return ID.
     */
    protected Integer getCurrentIdRole() {
    	return getCurrentUser().getIdRole();
    }
}
