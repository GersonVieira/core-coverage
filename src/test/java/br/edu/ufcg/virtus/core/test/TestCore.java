package br.edu.ufcg.virtus.core.test;



import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.edu.ufcg.virtus.controller.UserController;
import br.edu.ufcg.virtus.core.dto.LoggedUserDTO;
import br.edu.ufcg.virtus.core.dto.PageListDTO;
import br.edu.ufcg.virtus.core.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.core.exception.BusinessException;
import br.edu.ufcg.virtus.core.security.AccountCredentials;
import br.edu.ufcg.virtus.core.util.JSonUtil;
import br.edu.ufcg.virtus.model.Carro;
import br.edu.ufcg.virtus.service.CarroService;
import br.edu.ufcg.virtus.service.UserService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SpringBootTest()
public class TestCore {

    @Autowired
    UserController userC;

    @Autowired
    UserService userS;

    @Autowired
    CarroService carroS;

    LoggedUserDTO user;

    @Test
    public void asd() throws ServletException, BusinessException {
        this.requestWithOutToken();
        this.failLogin();
        this.login();
        this.postNewCar("Uno 1");
        this.postNewCar("Uno 2");
        this.postNewCar("Uno 3");
        this.postNewCar("Uno 4");
        this.postNewCar("Uno 5");
        this.deleteOneCar(1);
        this.putCar(3, "Uno put");
        this.patchCar(2, "Uno patch");
        this.getAllCars();
        this.getAllCarsWithFilter();
        this.getAllCustom();
        this.getOneCar(2);
        this.personPostCar409("Uno put");
        this.getCustomSearch();
        this.personDelete();
        this.notAcceptable();
        this.personMessage();
    }

    void failLogin() {
        RestAssured.baseURI ="http://localhost:8080/core/v1";
        final AccountCredentials cred = new AccountCredentials();
        cred.setUsername("demo@virtus.ufcg.edu.br");
        cred.setPassword("masterzinho");

        final Response response = this.postWithBody(cred, "/login");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(403);
    }

    void login() {
        RestAssured.baseURI ="http://localhost:8080/core/v1";
        final AccountCredentials cred = new AccountCredentials();
        cred.setUsername("demo@virtus.ufcg.edu.br");
        cred.setPassword("master");

        final Response response = this.postWithBody(cred, "/login");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
        this.user = JSonUtil.fromJSon(response.getBody().asString(), LoggedUserDTO.class);
    }

    void requestWithOutToken() {
        RestAssured.baseURI ="http://localhost:8080/core/v1";
        final Response response = this.getResponse("/carros");
        response.getBody().prettyPrint();
        response.then().statusCode(403);
    }

    void getAndPostCar() {
        RestAssured.baseURI ="http://localhost:8080/core/v1";
        final Response response = this.getResponse("/carros");
        final PageListDTO page = JSonUtil.fromJSon(response.getBody().asString(), PageListDTO.class);
    }

    RequestSpecification buildHeaders() {
        final SearchFilterDTO filter = new SearchFilterDTO();
        filter.setColumn("name");
        final RequestSpecification request = RestAssured.given()
                .accept("*/*")
                .contentType("application/json")
                .header("Accept-Encoding", "gzip, deflate, br");
        if(this.user != null) {
            request.header("Authorization", String.format("Bearer %s", this.user.getToken()));
        }
        return request;

    }

    RequestSpecification buildHeadersWithFilter() {
        final SearchFilterDTO filter = new SearchFilterDTO();
        filter.setColumn("name");
        filter.setSearch("11111");
        final RequestSpecification request = RestAssured.given()
                .accept("*/*")
                .contentType("application/json")
                .header("Accept-Encoding", "gzip, deflate, br").queryParam("filter", JSonUtil.toJSon(filter));
        if(this.user != null) {
            request.header("Authorization", String.format("Bearer %s", this.user.getToken()));
        }
        return request;

    }

    void putCar(Integer id, String carName) {
        final Carro carro = new Carro();
        carro.setId(id);
        carro.setName(carName);
        final Response response = this.putWithBody(carro, "/carros/"+id);
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void patchCar(Integer id, String carName) {
        final Carro carro = new Carro();
        carro.setId(id);
        carro.setName(carName);
        final Response response = this.patchWithBody(carro, "/carros/"+id);
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void personPostCar409(String carName) {
        final Carro carro = new Carro();
        carro.setName(carName);
        final Response response = this.postWithBody(carro, "/carros/person-post");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(409);
    }

    void postNewCar(String carName) {
        final Carro carro = new Carro();
        carro.setName(carName);
        final Response response = this.postWithBody(carro, "/carros");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(201);
    }

    void deleteOneCar(Integer id) {
        final Response response = this.delete("/carros/"+id);
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);

    }

    void personDelete() {
        final Response response = this.delete("/carros/person-delete");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);

    }

    Response postWithBody(Object body, String url) {
        return this.buildHeaders()
                .body(JSonUtil.toJSon(body))
                .post(url);
    }

    Response putWithBody(Object body, String url) {
        return this.buildHeaders()
                .body(JSonUtil.toJSon(body))
                .put(url);
    }

    Response getAllWithFilter(String url) {
        return this.buildHeadersWithFilter()
                .get(url);
    }

    Response getAll(String url) {
        return this.buildHeaders()
                .get(url);
    }

    Response getOne(String url, Integer id) {
        return this.buildHeaders().get(url+"/"+id);
    }


    Response getAllCustom(String url) {
        return this.buildHeaders()
                .get(url);
    }

    Response patchWithBody(Object body, String url) {
        return this.buildHeaders()
                .body(JSonUtil.toJSon(body))
                .patch(url);
    }

    Response delete(String url) {
        return this.buildHeaders()
                .delete(url);
    }

    Response getResponse(String url) {
        return this.buildHeaders().get(url);
    }

    void getAllCarsWithFilter() {
        final Response response = this.getAllWithFilter("/carros");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getAllCars() {
        final Response response = this.getAll("/carros");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getCustomSearch() {
        final Response response = this.getAll("/carros/person-search");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    private void getOneCar(Integer id) {
        final Response response = this.getOne("/carros", id);
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getAllCustom() {
        final Response response = this.getAllCustom("/carros/get-all-simple");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void notAcceptable() {
        final Response response = this.getAll("/carros/not-acceptable/123");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(406);
    }

    void personMessage() {
        final Response response = this.getAll("/carros/person-message");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

}
