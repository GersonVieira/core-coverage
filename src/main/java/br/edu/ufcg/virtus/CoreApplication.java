package br.edu.ufcg.virtus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.edu.ufcg.virtus.core.config.AppContext;

/**
 * Main class of application.
 * 
 * @author Virtus
 */
@SpringBootApplication
@EnableCaching
@EnableWebMvc
public class CoreApplication {

	/**
	 * Main.
	 * 
	 * @param args
	 * 		Arguments.
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CoreApplication.class, args);
		
		AppContext.loadApplicationContext(ctx);
	}
}
