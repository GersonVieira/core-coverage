package br.edu.ufcg.virtus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.core.model.Model;

/**
 * Model for table: User Account.
 *
 * @author Virtus
 */
@Entity(name = "user_account")
@SQLDelete(sql = "UPDATE user_account SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
public class User extends Model<Integer> {

    /**
     * User ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Name.
     */
    @Column(name = "name")
    private String name;

    /**
     * Username.
     */
    @Column(name = "username")
    private String username;

    /**
     * Password.
     */
    @Column(name = "password")
    private String password;

    /**
     * Role ID.
     */
    @Column(name = "idrole")
    private Integer idRole;
    
    /**
     * If it is deleted.
     */
    @Column(name = "deleted")
    private boolean deleted;
    
    /**
     * Constructor.
     */
    public User() {
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
     * @param name Name.
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
     * Gets the password.
     *
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password Password.
     */
    public void setPassword(String password) {
        this.password = password;
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
	 * @param idRole Role ID.
	 */
	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}
    
    /**
     * Gets if it is deleted.
     * 
     * @return If it is deleted.
     */
    public boolean isDeleted() {
		return deleted;
	}

    /**
     * Sets if it is deleted.
     * 
     * @param deleted
     * 		If it is deleted.
     */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

    /**
     * (non-Javadoc)
     *
     * @see br.edu.ufcg.virtus.core.model.Model#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * (non-Javadoc)
     * @see br.edu.ufcg.virtus.core.model.Model#setId(java.io.Serializable)
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }
}
