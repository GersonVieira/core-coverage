package br.edu.ufcg.virtus.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.edu.ufcg.virtus.core.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.model.User;

/**
 * CRUD Repository for entity: User.
 *
 * @author Virtus
 */
public interface UserRepository extends CrudBaseRepository<User, Integer> {

    /**
     * Login the user.
     *
     * @param username
	 * 		Username.
     * @param encryptedPassword 
     * 		Encrypted password.
     * @return User's data.
     */
    @Query(value = "select * from user_account where upper(username) = upper(?1) and password = ?2 and deleted = false", nativeQuery = true)
    User login(String username, String encryptedPassword);

    /**
     * Checks if exists an user with the same username.
     * 
     * @param username
     * 		Username.
     * @return If exists an user with the same username.
     */
    boolean existsByUsername(String username);
    
    /**
     * Checks if exists an user with the same username and
     * it is not the same as the ID specified.
     * 
     * @param username
     * 		Username.
     * @param id
     * 		ID.
     * @return If exists an user with these filters.
     */
    boolean existsByUsernameAndIdNot(String username, Integer id);
    
    /**
     * Finds the user by username.
     * 
     * @param username
     * 		Username.
     * @return User.
     */
    User findByUsername(String username);

    /**
     * Checks if the password of the user is correct.
     * 
     * @param id
     * 		User ID.
     * @param password
     * 		Password.
     * @return If the password of the user is correct.
     */
    @Query(value = "select 1 from user_account where id = ?1 and password = ?2 and deleted = false", nativeQuery = true)
	Integer isPasswordCorrect(Integer id, String password);
    
    /**
     * Updates the password of the specified user.
     * 
     * @param id
     * 		User ID.
     * @param newPassword
     * 		New password.
     */
    @Modifying
    @Query(value = "update user_account set password = ?2 where id = ?1 and deleted = false", nativeQuery = true)
    void updatePassword(Integer id, String newPassword);
}
