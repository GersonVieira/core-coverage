package br.edu.ufcg.virtus.controller;

import br.edu.ufcg.virtus.core.api.ApiVersion;
import br.edu.ufcg.virtus.core.api.ApiVersions;
import br.edu.ufcg.virtus.core.config.RestExceptionHandler;
import br.edu.ufcg.virtus.core.controller.CrudBaseController;
import br.edu.ufcg.virtus.core.domain.Comparison;
import br.edu.ufcg.virtus.core.dto.*;
import br.edu.ufcg.virtus.core.exception.BusinessException;
import br.edu.ufcg.virtus.core.exception.RestException;
import br.edu.ufcg.virtus.core.security.TokenAuthenticationService;
import br.edu.ufcg.virtus.core.service.CrudService;
import br.edu.ufcg.virtus.core.util.CryptoUtil;
import br.edu.ufcg.virtus.core.util.MessageUtil;
import br.edu.ufcg.virtus.dto.CarroDTO;
import br.edu.ufcg.virtus.dto.UserDTO;
import br.edu.ufcg.virtus.model.Carro;
import br.edu.ufcg.virtus.service.CarroService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("carros")
@Api(value = "carros", tags = "carros-controller")
@ApiVersion(ApiVersions.V1)
public class CarroController extends CrudBaseController<Carro, Integer, CarroDTO> {

    @Autowired
    private CarroService carroService;

    @Autowired
    private TokenAuthenticationService authService;

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
        BusinessException ex = new BusinessException(acceptable);
        return this.notAcceptable(ex);
    }

    @GetMapping("/person-message")
    public ResponseEntity<RestMessageDTO> personMessage(HttpServletRequest request) {
        List<String> strings = new ArrayList<>();
        strings.add("Message 1");
        RestMessageDTO message = new RestMessageDTO(MessageUtil.findMessage("200"));
        RestMessageDTO message2 = new RestMessageDTO("message","250");
        RestMessageDTO message3 = new RestMessageDTO("message","250");
        RestMessageDTO message4 = new RestMessageDTO(strings);
        message.setCode("499");
        try {
            MessageUtil.findMessage(null);
            MessageUtil.findMessage(null, Locale.CANADA);
        } catch (Exception e) {
            System.out.println("exception");
        }
        try {
            CryptoUtil.hash(null);
        }catch (Exception e) {

        }
        try {
            this.carroService.getCarroDTOByFromResult();
        }catch (Exception e) {

        }
        message.setMessage("Personalized => "+ MessageUtil.findMessage("200"));
        message.setMessages(new ArrayList<>());
        return this.ok(message);
    }

    @GetMapping("/user-permissions")
    public ResponseEntity<List<String>> personMessage() {

        return this.ok(this.carroService.currentUser().getPermissions());
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken() {
        TokenDTO token = new TokenDTO();
        token.setToken(this.carroService.currentUser().getToken());
        return this.ok(token.getToken());
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> getRefreshToken() {
        return this.ok(this.carroService.currentUser().getRefreshToken());
    }

    @GetMapping("/name")
    public ResponseEntity<String> getloggedUserName() {
        new LoggedUserDTO(1, "gersin");
        return this.ok(this.carroService.currentUser().getName());
    }

    @GetMapping("/exception")
    public ResponseEntity<?> exception() throws BusinessException {
        try {
            int a = 1 / 0 ;
        } catch (Exception e) {
            BusinessException ex = new BusinessException("409");
            ex.setCode("409");
            throw ex;
        }
        return this.success();
    }

    @GetMapping("/rest-exception")
    public ResponseEntity<?> restException() throws RestException {
        try {
            RestException ex = new RestException("409", new String[]{"123","1234"});
            int a = 1 / 0 ;
        } catch (Exception e) {
            RestException ex = new RestException("409", new String[]{"123","1234"});
            throw ex;
        }
        return this.success();
    }

    @Override
    protected SearchFilterDTO makeSearchFilter(HttpServletRequest request) {
        SearchFilterDTO search = new SearchFilterDTO();
        List<FilterDTO> filters = new ArrayList<>();
        FilterDTO filter = new FilterDTO("name", "no", Comparison.LIKE);
        filter.setComparison(Comparison.LIKE);
        filter.setField("name");
        filter.setValue("no");
        filters.add(filter);
        search.setFilters(filters);
        return search;
    }

    @GetMapping("/generate-token")
    public ResponseEntity<String> generateToken(HttpServletRequest request) {
        LoggedUserDTO user = this.carroService.currentUser();
        String token = this.authService.generateToken(""+ user.getId(),user.getUsername());
        Authentication token2 = this.authService.getRefreshAuthetication(request);
        return this.ok(token);
    }

    @GetMapping("/rest-exceptions")
    public ResponseEntity<String> restExceptions(HttpServletRequest request) {
        throw new RestException("Alou", new Object[0]);
    }

    @GetMapping("/argument-rest-exceptions")
    public ResponseEntity<String> argumentRestExceptions(HttpServletRequest request) throws MethodArgumentNotValidException {
        throw new MethodArgumentNotValidException(null, null);
    }
}
