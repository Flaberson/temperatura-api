package br.com.flaberson.temperatura;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wather",produces = "application/json")
public class TemperaturaController {
	
	private String token = "88989b3b57b40f9f7f90f588aafb4006";
	private int temperatura;
	private String cidade; 

    @GetMapping
    public ResponseEntity<String> get(@RequestParam("longitude") String longitude,@RequestParam("latitude") String latitude) throws IOException{
    	
    	HttpConnection http = new HttpConnection();
    	
    	//CONSULTA CIDADE PELA LONGITUDE E LATITUDE
    	JSONObject jsonCidade = http.requestServico("http://apiadvisor.climatempo.com.br/api/v1/locale/city?latitude="+latitude+"&longitude="+longitude+"&token="+this.token);
    	int idCidade = jsonCidade.getInt("id");
    	
    	//CONSULTA CIDADE PELA LONGITUDE E LATITUDE
    	JSONObject jsonTemperatura = http.requestServico("http://apiadvisor.climatempo.com.br/api/v1/weather/locale/"+idCidade+"/current?token="+this.token);
    	
		
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
