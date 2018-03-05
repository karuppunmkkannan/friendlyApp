/**
 * 
 */
package com.friendly.friendlyApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author karuppusamy
 * @version 1.0
 *
 */

@EnableAutoConfiguration
@SpringBootApplication
@EntityScan("com.friendly.model")
@ComponentScan("com.friendly")
@EnableJpaRepositories("com.friendly.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
