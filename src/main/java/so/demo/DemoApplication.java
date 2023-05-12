package so.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class DemoController{
	Map<Integer,String> messages = Map.of(1,"Hello 1", 2 , "Hello 2", 3, "Hello 3" , 4, "Hello 4", 5, "Hello 5" );

	@GetMapping("/multi") // http://localhost:8080/multi?id=1&id=2&id=3
	List<String> messages(@RequestParam(value = "id") List<Integer> ids){
		// do the lookup by ids...
		return messages.entrySet().stream()
				.filter( e -> ids.contains(e.getKey()))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}

	@GetMapping("/array") // => http://localhost:8080/array?id[in]=1,2,3
	List<String> messages(@RequestParam(value = "id[in]") String ids) {
		// get the actual ids from the string
		final List<Integer> idList =
				Arrays.stream(ids.split(","))
						.map(id -> Integer.parseInt(id))
						.collect(Collectors.toList());
		// do your lookup by ids...
		return messages.entrySet().stream()
				.filter( e -> idList.contains(e.getKey()))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}

}