package br.edu.ufcg.virtus.controller;

import br.edu.ufcg.virtus.core.api.ApiVersion;
import br.edu.ufcg.virtus.core.api.ApiVersions;
import br.edu.ufcg.virtus.core.controller.CrudBaseController;
import br.edu.ufcg.virtus.core.dto.PageListDTO;
import br.edu.ufcg.virtus.core.dto.RestMessageDTO;
import br.edu.ufcg.virtus.core.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.core.exception.BusinessException;
import br.edu.ufcg.virtus.core.service.CrudService;
import br.edu.ufcg.virtus.dto.CarroDTO;
import br.edu.ufcg.virtus.model.Carro;
import br.edu.ufcg.virtus.service.CarroService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("carros")
@Api(value = "carros", tags = "carros-controller")
@ApiVersion(ApiVersions.V1)
public class CarroController extends CrudBaseController<Carro, Integer, CarroDTO> {

    @Autowired
    private CarroService carroService;

    @Override
    protected CrudService getService() {
        return this.carroService;
    }

    @GetMapping("/get-all-simple")
    public ResponseEntity<List<CarroDTO>> getOneByID() {

        return this.responseEntity(this.toListDTO(this.carroService.getAllDTO()), HttpStatus.OK);
    }

    @PostMapping("/person-post")
    public ResponseEntity<CarroDTO> personPut(@RequestBody Carro carro) {
        try {
            return this.created(this.toDTO(this.carroService.insert(carro)));
        } catch ( BusinessException e) {
            return this.conflitct(e);
        }
    }

    @GetMapping("/person-search")
    public ResponseEntity<PageListDTO> personSearch() {
        SearchFilterDTO filter = new SearchFilterDTO();
        filter.setColumn("name");
        filter.setSearch("esla");
        filter.setCurrentPage(1);
        filter.setPageSize(3);
        filter.setSort("desc");
        filter.setFilters(new ArrayList<>());
        return this.ok(this.carroService.search(filter));
    }

    @DeleteMapping("/person-delete")
    public ResponseEntity<?> personDelete() {
        List<Integer> ids = new ArrayList<>();
        ids.add(3);
        ids.add(4);
        try {
            this.carroService.delete(ids);
        }catch (BusinessException e) {
            return this.conflitct(e);
        }
        return this.success();
    }

    @GetMapping("/not-acceptable/{acceptable}")
    public ResponseEntity<?> notAcceptable(HttpServletRequest request) {
        String acceptable = this.getPathVariable(request, "acceptable");
        return this.notAcceptable(new BusinessException("Alou"));
    }

    @GetMapping("/person-message")
    public ResponseEntity<RestMessageDTO> personMessage(HttpServletRequest request) {
        List<String> strings = new ArrayList<>();
        strings.add("Message 1");
        RestMessageDTO message = new RestMessageDTO("");
        RestMessageDTO message2 = new RestMessageDTO("message","250");
        RestMessageDTO message3 = new RestMessageDTO("message","250");
        RestMessageDTO message4 = new RestMessageDTO(strings);
        message.setCode("499");
        message.setMessage("Personalized");
        message.setMessages(new ArrayList<>());
        return this.ok(message);
    }
}
