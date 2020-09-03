package br.edu.ufcg.virtus.service;

import br.edu.ufcg.virtus.core.exception.BusinessException;
import br.edu.ufcg.virtus.core.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.service.CrudService;
import br.edu.ufcg.virtus.dto.CarroDTO;
import br.edu.ufcg.virtus.model.Carro;
import br.edu.ufcg.virtus.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService extends CrudService<Carro, Integer> {

    @Autowired
    private CarroRepository carroRepository;

    @Override
    protected CrudBaseRepository<Carro, Integer> getRepository() {
        return this.carroRepository;
    }

    @Override
    protected void validateInsert(Carro model) throws BusinessException {
        super.validateInsert(model);
        if(this.carroRepository.existsByName(model.getName())) {
            throw new BusinessException("carro.name.exists");
        }
    }

    public List<Carro> getAllDTO () {
        return this.carroRepository.findAllCustom();
    }

}
