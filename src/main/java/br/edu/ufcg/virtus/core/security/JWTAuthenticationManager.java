package br.edu.ufcg.virtus.core.security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.edu.ufcg.virtus.core.config.AppContext;
import br.edu.ufcg.virtus.core.dto.LoggedUserDTO;
import br.edu.ufcg.virtus.model.User;
import br.edu.ufcg.virtus.service.PermissionService;
import br.edu.ufcg.virtus.service.UserService;

/**
 * Authentication Manager.
 *
 * @author Virtus
 *
 */
public class JWTAuthenticationManager implements AuthenticationManager {

    /**
     * User service.
     */
    private UserService userService;

    /**
     * Permission service.
     */
    private PermissionService permissionService;

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationManager#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        User loggedUser = this.getUserService().login(auth.getName(), (String) auth.getCredentials());

        if (loggedUser != null) {
            return new UsernamePasswordAuthenticationToken(toUserDTO(loggedUser), auth.getCredentials());
        }

        throw new BadCredentialsException("Invalid username and/or password.");
    }
    
    /**
     * Add user's permissions to the DTO.
     * 
     * @param dto
     * 		Logged User DTO.
     * @param idRole
     * 		Role ID.
     * @return DTO with permissions.
     */
    public LoggedUserDTO addPermissions(LoggedUserDTO dto, Integer idRole) {
    	dto.setPermissions(new ArrayList<>(getPermissionService().getPermissions(idRole)));
    	return dto;
    }

    /**
     * Gets the User service.
     *
     * @return User service.
     */
    protected UserService getUserService() {

        if (this.userService == null) {
            this.userService = AppContext.getBean(UserService.class);
        }

        return this.userService;
    }
    
    /**
     * Gets the Permission service.
     *
     * @return Permission service.
     */
    protected PermissionService getPermissionService() {
    	
    	if (this.permissionService == null) {
    		this.permissionService = AppContext.getBean(PermissionService.class);
    	}
    	
    	return this.permissionService;
    }
    
    /**
     * Converts the Logged User to DTO.
     *
     * @param user
     *            Logged User.
     * @return DTO.
     */
    private LoggedUserDTO toUserDTO(User user) {

    	LoggedUserDTO userDTO = new LoggedUserDTO();

    	userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setIdRole(user.getIdRole());
        this.addPermissions(userDTO, user.getIdRole());

        return userDTO;
    }
}
