package br.com.flaberson.temperatura;
import java.io.IOException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/wather",produces = "application/json")
public class TemperaturaController {
	
	private String token = "88989b3b57b40f9f7f90f588aafb4006";
	private int temperatura;
	private String cidade; 
	final String URL_API = "http://apiadvisor.climatempo.com.br/api/v1";

    @GetMapping
    public ResponseEntity<String> get(@RequestParam(value="longitude", required=false) String longitude,@RequestParam(value = "latitude", required=false) String latitude) throws IOException{
    	
    	if(null==longitude || null==latitude) {
    		JSONObject json = new JSONObject();
        	json.put("status", "erro");
        	json.put("msg", "Informe a latitude e longitude");    		
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    	}     	
    	
    	HttpConnection http = new HttpConnection();
    	
    	//CONSULTA CIDADE PELA LONGITUDE E LATITUDE
    	JSONObject jsonCidade = http.requestServico(URL_API + "/locale/city?latitude="+latitude+"&longitude="+longitude+"&token="+this.token);
    	int idCidade = jsonCidade.getInt("id");
    	
    	//CONSULTA CIDADE PELA LONGITUDE E LATITUDE
    	JSONObject jsonTemperatura = http.requestServico(URL_API + "/weather/locale/"+idCidade+"/current?token="+this.token);
		
		JSONObject data = jsonTemperatura.getJSONObject("data");
		temperatura = data.getInt("temperature");
		cidade = jsonTemperatura.getString("name");
		
    	//RETORNO DO CONTEUDO 
    	JSONObject json = new JSONObject();
    	json.put("temperatura", temperatura);
    	json.put("cidade", cidade);
		
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    	
    }

}
