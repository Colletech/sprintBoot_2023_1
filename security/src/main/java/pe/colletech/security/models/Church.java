package pe.colletech.security.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Church {

	private Long id;

	private String idMunicipality;

	private Boolean baptized;
}
