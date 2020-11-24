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
import br.edu.ufcg.virtus.core.dto.TokenDTO;
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
        //this.failLogin();
        //this.swagger();
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
        //        this.getAllCustom();
        //        this.getOneCar(2);
        //        this.personPostCar409("Uno put");
        //
        //        this.getCustomSearch();
        //        this.personDelete();
        //
        //
        //        this.notAcceptable();
        //
        //
        //        this.personMessage();
        //        this.getAllUser();
        //        this.getPermissions();
        //
        //
        //        this.getException();
        //        this.getRestException();
        //
        //
        //        this.getToken();
        //        this.getRefreshToken();
        //        this.getloggedUserName();
        //        this.generateToken();
        //        this.inspiredToken();
        //        this.inspiredTokenNullValue();

    }

    void failLogin() {
        RestAssured.baseURI ="http://localhost:5000/core/v1";
        final AccountCredentials cred = new AccountCredentials();
        cred.setUsername("demo@virtus.ufcg.edu.br");
        cred.setPassword("masterzinho");

        final Response response = this.postWithBody(cred, "/login");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(403);
    }

    void swagger() {
        RestAssured.baseURI ="http://localhost:5000";
        final Response response = this.getAll("/swagger/v1/swagger.json");
        response.getBody().toString();
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void login() {
        RestAssured.baseURI ="http://localhost:5000/core/v1";
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
        final RequestSpecification request = RestAssured.given().redirects().max(100).redirects().follow(true)
                .accept("*/*")
                .contentType("application/json")
                .header("Accept-Encoding", "gzip, deflate, br").header("Accept", "*/*").header("Connection","keep-alive");
        if(this.user != null) {
            request.header("Authorization", String.format(this.user.getToken()));
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
            request.header("Authorization", String.format(this.user.getToken()));
        }
        return request;

    }

    RequestSpecification buildHeadersWithFilterFail() {
        final SearchFilterDTO filter = new SearchFilterDTO();
        filter.setColumn("name");
        filter.setSearch("a");
        final RequestSpecification request = RestAssured.given()
                .accept("*/*")
                .contentType("application/json")
                .header("Accept-Encoding", "gzip, deflate, br").queryParam("filter", JSonUtil.toJSon(filter));
        if(this.user != null) {
            request.header("Authorization", String.format(this.user.getToken()));
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

    Response getAllWithFilterFail(String url) {
        return this.buildHeadersWithFilterFail()
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
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void restExceptions() {
        final Response response = this.getAllWithFilterFail("/carros/rest-exceptions");
        response.getBody().prettyPrint();
        response.then().statusCode(500);
    }

    void argumentRestExceptions() {
        final Response response = this.getAllWithFilterFail("/carros/argument-rest-exceptions");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getAllCars() {
        final Response response = this.getAll("/carros");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getCustomSearch() {
        final Response response = this.getAll("/carros/person-search");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    private void getOneCar(Integer id) {
        final Response response = this.getOne("/carros", id);
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getAllCustom() {
        final Response response = this.getAllCustom("/carros/get-all-simple");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void notAcceptable() {
        final Response response = this.getAll("/carros/not-acceptable/123");
        response.getBody().prettyPrint();
        response.then().statusCode(406);
    }

    void personMessage() {
        final Response response = this.getAll("/carros/person-message");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getAllUser() {
        final Response response = this.getAll("/users");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getPermissions() {
        final Response response = this.getAll("carros/user-permissions");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getToken() {
        final Response response = this.getAll("carros/token");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getRefreshToken() {
        final Response response = this.getAll("carros/refresh-token");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getloggedUserName() {
        final Response response = this.getAll("carros/name");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void getException() {
        final Response response = this.getAll("carros/exception");
        response.getBody().prettyPrint();
        response.then().statusCode(409);
    }

    void getRestException() {
        final Response response = this.getAll("carros/rest-exception");
        response.getBody().prettyPrint();
        response.then().statusCode(500);
    }

    void generateToken() {
        final Response response = this.getAll("carros/generate-token");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
    }

    void inspiredToken() {
        System.out.println("-------------------------");
        this.user.setToken("Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwic3ViIjoiZGVtb0B2aXJ0dXMudWZjZy5lZHUuYnIiLCJyb2xlIjoxLCJleHAiOjE1OTkwMDAwMDB9.0nDqbzpiE2F4dRdm8uIzVVDQtq1sc5Zj3UWrUcri-A56b7VUyQ9HRNepw6_IveH104jIytV9BD5tCgkjjPiDEA");
        final TokenDTO token = new TokenDTO();
        token.setToken(this.user.getRefreshToken());
        final Response response = this.postWithBody(token, "/refresh");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
        System.out.println("-------------------------");
    }

    void inspiredTokenNullValue() {
        System.out.println("-------------------------");
        this.user.setToken("Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwic3ViIjoiZGVtb0B2aXJ0dXMudWZjZy5lZHUuYnIiLCJyb2xlIjoxLCJleHAiOjE1OTkwMDAwMDB9.0nDqbzpiE2F4dRdm8uIzVVDQtq1sc5Zj3UWrUcri-A56b7VUyQ9HRNepw6_IveH104jIytV9BD5tCgkjjPiDEA");
        final TokenDTO token = new TokenDTO();
        final Response response = this.postWithBody(token, "/refresh");
        response.getBody().prettyPrint();
        response.then().statusCode(200);
        System.out.println("-------------------------");
    }

}
