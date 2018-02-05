package com.cromecast.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cromecast.domain.Product;
import com.cromecast.model.Fibonacci;
import com.cromecast.model.Paragraph;
import com.cromecast.model.ProductResult;
import com.cromecast.model.User;
import com.cromecast.repositories.ProductRepository;
import com.cromecast.response.FibonacciResponse;
import com.cromecast.response.ParagraphResponse;
import com.cromecast.service.FibonacciService;
import com.cromecast.service.RestTemplateService;
import com.cromecast.service.UniqueWordsService;

@RestController
public class RestfulServiceController {
	private Logger log = Logger.getLogger(RestfulServiceController.class);

	@Autowired
	UniqueWordsService wordservice;

	@Autowired
	FibonacciService fibonacciService;

	@Autowired
	RestTemplateService restBuilder;

	@Autowired
	ProductRepository productRepository;

	@Value("${external.service.url}")
	private String serviceUrl;

	@RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
	ResponseEntity<String> welcome() throws Exception {
		return new ResponseEntity<String>("Hello World", HttpStatus.OK);
	}

	@RequestMapping(value = "/findUniqueWords", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ParagraphResponse findUniqueWords(
			@RequestBody Paragraph paragraphRequest) throws Exception {

		ParagraphResponse response = new ParagraphResponse();
		if (!paragraphRequest.getParagraphText().isEmpty()) {
			String line[] = paragraphRequest.getParagraphText().split("\n");
			response.setUniqueWords(wordservice.getFrequency(line));
		}
		return response;
	}

	@RequestMapping(value = "/findFibonacci", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody FibonacciResponse findFibonacciNumber(
			@RequestBody Fibonacci numberRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FibonacciResponse fibonacciResponse = new FibonacciResponse();
		if (numberRequest.getFibonacciNumbers() != 0) {
			/*List<Integer> result = fibonacciService
					.generateFibonacci(numberRequest.getFibonacciNumbers());
					fibonacciResponse.setFibonacciResult(result);
			*/			
			
			fibonacciResponse.setFibonacciResult(fibonacciService.recursiveFibonacci(numberRequest.getFibonacciNumbers()));

		}
		return fibonacciResponse;

	}

	@RequestMapping(value = "/accessExternalCall", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<User> restCall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return restBuilder.externalWebServiceCall(User.class, serviceUrl);
	}

	@RequestMapping(value = "/findAllProduct", method = RequestMethod.GET)
	public @ResponseBody Iterable<Product> findProduct(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// uses the findAll() method inherited from CrudRepository
		return productRepository.findAll();
	}

	@RequestMapping(value = "/findProduct/{id}")
	public Product getProduct(@PathVariable("id") Integer id) {
		// uses the findOne() method inherited from CrudRepository
		return productRepository.findOne(id);
	}
	
	
	@RequestMapping(value = "/queryProduct/{productname}/{promotion}")
	public List<Product> queryProduct( @PathVariable("productname") String productname,@PathVariable("promotion") Integer promotion) {
		// uses the findOne() method inherited from CrudRepository
		return productRepository.fetchProduct(productname, promotion);
	}

	@RequestMapping(value = "/removeProduct/{id}", method = RequestMethod.DELETE)
	public String deleteProduct(@PathVariable("id") Integer id) {
		// uses the delete() method inherited from CrudRepository
		try {
			productRepository.delete(id);
			return String.format("Product [%s] remove successfully", id);
		} catch (Exception e) {
			return String.format(
					"A problem occurred when deleting Product [%s]",
					e.getMessage());
		}
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProductResult addProduct(
			@RequestBody Product addProduct, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Saved Product - id: " + addProduct.getId());
		ProductResult productResult = new ProductResult();

		if (!productRepository.exists(addProduct.getId())) {
			productRepository.save(addProduct);

			productResult.setMessage("Insert new record ");
			productResult.setId(addProduct.getId());
			return productResult;
		} else {
			productResult.setMessage("Record already exist:");
			productResult.setId(addProduct.getId());
			return productResult;
		}
		
	}

}