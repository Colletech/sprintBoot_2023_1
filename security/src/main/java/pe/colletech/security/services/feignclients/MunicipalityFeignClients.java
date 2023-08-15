package pe.colletech.security.services.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.colletech.security.models.Municipality;

@FeignClient(name = "municipality-service")
@RequestMapping(value = "/municipality")
public interface MunicipalityFeignClients {

	@PostMapping
	public Municipality savePerson(@RequestBody Municipality municipality);
	
	@GetMapping(value = "/person/{dni}")
	public Municipality getPerson(@PathVariable Long dni);
}
