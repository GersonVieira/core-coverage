package br.edu.ufcg.virtus.core.service;

import java.io.Serializable;

import br.edu.ufcg.virtus.core.dto.PageListDTO;
import br.edu.ufcg.virtus.core.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.core.model.Model;
import br.edu.ufcg.virtus.core.repository.SearchBaseRepository;

/**
 * The 'SearchService' provides the common API for all services that
 * do search operations with models.
 * 
 * All search model services MUST extend this class.
 * 
 * @author Virtus
 *
 * @param <M> Model type.
 * @param <T> ID type.
 */
public abstract class SearchService<M extends Model<T>, T extends Serializable> extends BaseService {

	/**
	 * Gets the search Repository.
	 * 
	 * @return Search Repository.
	 */
	protected abstract SearchBaseRepository<M, T> getRepository();
	
	/**
	 * Searches the model with the filter.
	 * 
	 * @param filter
	 * 		Filter.
	 * @return Instances founded.
	 */
	public PageListDTO search(SearchFilterDTO filter) {
		return getRepository().search(filter);
	}

	/**
	 * Gets one model instance by ID.
	 * 
	 * @param id
	 * 		ID.
	 * @return Instance founded.
	 */
	public M getOne(T id) {
		return getRepository().findById(id).get();
	}
}
