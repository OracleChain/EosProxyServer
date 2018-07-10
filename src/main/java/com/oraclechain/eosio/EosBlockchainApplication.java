package com.oraclechain.eosio;

//import com.github.pagehelper.PageHelper;
import com.oraclechain.eosio.constants.SpringContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class EosBlockchainApplication extends SpringBootServletInitializer {

	public static void main(String[] args)
	{
		SpringApplication.run(EosBlockchainApplication.class, args);
	}

    @Bean
    public SpringContext create(){
        return new SpringContext();
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EosBlockchainApplication.class);
	}

}

