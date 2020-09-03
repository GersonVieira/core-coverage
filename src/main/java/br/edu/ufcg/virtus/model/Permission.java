package br.edu.ufcg.virtus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.edu.ufcg.virtus.core.model.Model;

/**
 * Model for table: Permission.
 * 
 * @author Virtus
 */
@Entity(name = "permission")
public class Permission extends Model<Integer> {

	/**
	 * Permission ID.
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
	 * Description
	 */
	@Column(name = "description")
	private String description;
	
	/**
	 * (non-Javadoc)
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
	 * Gets the description.
	 * 
	 * @return Description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description Description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
