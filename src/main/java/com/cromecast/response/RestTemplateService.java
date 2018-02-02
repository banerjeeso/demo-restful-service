package com.cromecast.response;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestTemplateService {

	Logger log = LoggerFactory.getLogger(RestTemplateService.class);

	public <T> List<T> externalWebServiceCall(Class<T> responseType, String url)
			throws JsonParseException, JsonMappingException, IOException {

		ResponseEntity<String> responseObj = null;
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain,
				String authType) -> true;

		
		ResponseEntity<String> response = null;
		List<T> users= null;
		try {
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy)
					.build();
			
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			

			RestTemplate restTemplate = new RestTemplate(requestFactory);			
			ObjectMapper mapper = new ObjectMapper();
			HttpHeaders headers = new HttpHeaders();
			
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);			
			HttpEntity entity = new HttpEntity<>(headers);			
			
			responseObj = restTemplate.exchange(url, HttpMethod.GET, entity,String.class);	
			TypeReference ref = new TypeReference<List<T>>() {	};
			users= mapper.readValue(responseObj.getBody(), ref);				

			if (isError(responseObj.getStatusCode())) {
				log.info("Error! No Further processing");				
			} else {
				log.info("Status code received : "
						+ responseObj.getStatusCode());
			}

		} catch (KeyManagementException | NoSuchAlgorithmException
				| KeyStoreException e) {
			log.error("Exception occured. Here are the exception details: ", e);
		} catch (HttpClientErrorException e) {
			if (e.getRawStatusCode() == 403) {
				log.info("Status code received: "
						+ e.getRawStatusCode()
						+ ". You do not have access to the requested resource.");

			} else if (e.getRawStatusCode() == 404) {
				log.info("Status code received: "
						+ e.getRawStatusCode()
						+ ". Resource does not exist(or) the service is not up.");

			} else if (e.getRawStatusCode() == 400) {
				log.info("Status code received: "
						+ e.getRawStatusCode()
						+ ". Bad Request.");

			} else {
				log.info("Status code received: "
						+ e.getRawStatusCode() + ".");

			}
			log.info("Response body: "
					+ e.getResponseBodyAsString() );
		}
		return users;
	}

	protected boolean isError(HttpStatus status) {
		HttpStatus.Series series = status.series();
		return HttpStatus.Series.CLIENT_ERROR.equals(series)
				|| HttpStatus.Series.SERVER_ERROR.equals(series);
	}
}
