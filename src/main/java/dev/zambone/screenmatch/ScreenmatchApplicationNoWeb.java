package dev.zambone.screenmatch;

import dev.zambone.screenmatch.main.Main;
import dev.zambone.screenmatch.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationNoWeb implements CommandLineRunner {
//
//	@Autowired
//	private TvShowRepository tvShowRepository;
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplicationNoWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Main main = new Main(tvShowRepository);
//		main.showMenu();
//	}
//}