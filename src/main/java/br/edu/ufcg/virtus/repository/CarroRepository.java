package br.edu.ufcg.virtus.repository;

import br.edu.ufcg.virtus.core.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.dto.CarroDTO;
import br.edu.ufcg.virtus.model.Carro;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarroRepository extends CrudBaseRepository<Carro, Integer> {


    /**
     * Checks if exists an user with the same name.
     *
     * @param name
     * 		Name.
     * @return If exists an user with the same name.
     */
    boolean existsByName(String name);

    @Query(nativeQuery = true, value = "select * from carro_default")
    List<Carro> findAllCustom();

    @Query(nativeQuery = true, value = "select * from carro_default")
    Iterable<Object[]> findAllCustomIterable();

    default List<CarroDTO>  transforList() {
        String[] a = new String[2];
        a[0] = "id";
        a[1] = "name";
        return this.fromResult(CarroDTO.class, this.findAllCustomIterable(), a);
    }

}
