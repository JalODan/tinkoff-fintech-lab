package kz.oj.tinkofffintechlab;

import org.springframework.boot.SpringApplication;

public class TestTinkoffFintechLabApplication {

	public static void main(String[] args) {
		SpringApplication.from(TinkoffFintechLabApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
