package dev.zambone.screenmatch;

import dev.zambone.screenmatch.model.TvShowModel;
import dev.zambone.screenmatch.service.RequestApi;
import dev.zambone.screenmatch.service.TvShowDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var requestApi = new RequestApi();
		var json = requestApi.getData("http://www.omdbapi.com/?t=gilmore+girls&apikey=b75c3faa");
		TvShowDTO converter = new TvShowDTO();
		TvShowModel data = converter.getData(json, TvShowModel.class);
		System.out.println(data);
	}
}