package br.edu.ufcg.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.virtus.core.exception.BusinessException;
import br.edu.ufcg.virtus.core.service.CrudService;
import br.edu.ufcg.virtus.core.util.CryptoUtil;
import br.edu.ufcg.virtus.model.User;
import br.edu.ufcg.virtus.repository.UserRepository;

/**
 * Crud Service for model: User.
 *
 * @author Virtus
 */
@Service
public class UserService extends CrudService<User, Integer> {

    /**
     * User repository.
     */
    @Autowired
    private UserRepository repository;

    /**
     * (non-Javadoc)
     *
     * @see br.edu.ufcg.virtus.core.service.CrudService#getRepository()
     */
    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    /**
     * (non-Javadoc)
     *
     * @see br.edu.ufcg.virtus.core.service.CrudService#insert(br.edu.ufcg.virtus.core.model.Model)
     */
    @Override
    public User insert(User user) throws BusinessException {
        user.setPassword(CryptoUtil.hash(user.getPassword()));

        return super.insert(user);
    }
    
    /**
     * (non-Javadoc)
     * @see br.edu.ufcg.virtus.core.service.CrudService#validateInsert(br.edu.ufcg.virtus.core.model.Model)
     */
    @Override
    protected void validateInsert(User model) throws BusinessException {
    	/* If username already exists */
    	if(getRepository().existsByUsername(model.getUsername())) {
    		throw new BusinessException("user.username.exists");
    	}
    }

    /**
     * (non-Javadoc)
     * @see br.edu.ufcg.virtus.core.service.CrudService#validateUpdate(br.edu.ufcg.virtus.core.model.Model)
     */
    @Override
    protected void validateUpdate(User model) throws BusinessException {
    	/* If username already exists and it is NOT me */
    	if(getRepository().existsByUsernameAndIdNot(model.getUsername(), model.getId())) {
    		throw new BusinessException("user.username.exists");
    	}
    }

    /**
     * Login the user.
     *
     * @param username Username.
     * @param password Password.
     * @return User.
     */
    public User login(String username, String password) {
        return getRepository().login(username, CryptoUtil.hash(password));
    }

    /**
     * Find the user by username.
     *
     * @param username Username.
     * @return User.
     */
    public User findByUsername(String username) {
        return getRepository().findByUsername(username);
    }
    
    /**
     * Updates the password of the current user.
     * 
     * @param currentPassword
     * 		Current password.
     * @param newPassword
     * 		New password.
     * @throws BusinessException
     */
    @Transactional
    public void updateCurrentPassword(String currentPassword, String newPassword) throws BusinessException {
    	this.updatePassword(getCurrentUserId(), currentPassword, newPassword);
    }
    
    /**
     * Updates the password of the specified user.
     * 
     * @param id
     * 		User ID.
     * @param currentPassword
     * 		Current password.
     * @param newPassword
     * 		New password.
     * @throws BusinessException
     */
    @Transactional
    public void updatePassword(Integer id, String currentPassword, String newPassword) throws BusinessException {
    	if(getRepository().isPasswordCorrect(id, CryptoUtil.hash(currentPassword)) == null) {
    		throw new BusinessException("user.password.wrong");
    	}
    	
    	getRepository().updatePassword(id, CryptoUtil.hash(newPassword));
    }
}
