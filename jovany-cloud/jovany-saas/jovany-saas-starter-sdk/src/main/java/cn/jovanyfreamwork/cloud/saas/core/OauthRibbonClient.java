package cn.jovanyfreamwork.cloud.saas.core;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OauthRibbonClient {

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public OauthRibbonClient(RestTemplate restTemplate, String clientSecret) {
		super();
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
		setClientSecret(clientSecret);
	}

	private String clientSecret;

	@SuppressWarnings("unchecked")
	public String token(String clientID) throws JsonParseException, JsonMappingException, IOException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "client_credentials");
		map.add("client_id", clientID);
		map.add("client_secret", clientSecret);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		header.setBasicAuth(clientID, clientSecret);
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, header);
		String response = restTemplate.postForObject("http://{learnyeaidemo-saas}/learnyeai/demo/saas/oauth/token",
				httpEntity, String.class);
		Map<String, String> json = objectMapper.readValue(response, Map.class);
		return "Bearer " + json.get("access_token");
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public OauthRibbonClient setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}
