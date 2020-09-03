package br.edu.ufcg.virtus.dto;

import br.edu.ufcg.virtus.core.dto.ModelDTO;

/**
 * DTO for entity: User.
 * 
 * @author Virtus
 */
public class UserDTO extends ModelDTO {

	/**
	 * User ID.
	 */
	private Integer id;

	/**
	 * Name.
	 */
    private String name;

    /**
     * Username.
     */
    private String username;

    /**
     * New Password.
     */
    private String newPassword;
    
    /**
     * Current Password.
     */
    private String currentPassword;

    /**
     * Role ID.
     */
    private Integer idRole;

    /**
     * Gets the ID.
     * 
     * @return ID.
     */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the ID.
	 * 
	 * @param id
	 * 		ID.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return Name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 * 		Name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Gets the username.
     * 
     * @return Username.
     */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 * 		Username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the new password.
	 * 
	 * @return New password.
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Sets the new password.
	 * 
	 * @param newPassword
	 * 		New password.
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	/**
	 * Gets the current password.
	 * 
	 * @return Current password.
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * Sets the current password.
	 * 
	 * @param currentPassword
	 * 		Current password.
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * Gets the role ID.
	 * 
	 * @return Role ID.
	 */
	public Integer getIdRole() {
		return idRole;
	}

	/**
	 * Sets the role ID.
	 * 
	 * @param idRole
	 * 		Role ID.
	 */
	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}
}
