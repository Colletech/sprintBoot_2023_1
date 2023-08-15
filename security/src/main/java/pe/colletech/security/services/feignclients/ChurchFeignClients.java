package pe.colletech.security.services.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.colletech.security.models.Church;

@FeignClient(name = "iglesia-service", url = "http://localhost:8080")
@RequestMapping(value = "/api")
public interface ChurchFeignClients {

	@PostMapping(value = "/iglesia")
	public Church saveCreyente(@RequestBody Church iglesia);

	@GetMapping(value = "/creyentes")
	public List<Church> getListCreyentes();
}
