package com.poc.austin.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

import com.poc.austin.domain.Product;
import com.poc.austin.model.Fibonacci;
import com.poc.austin.model.Paragraph;
import com.poc.austin.repositories.ProductRepository;
import com.poc.austin.response.FibonacciResponse;
import com.poc.austin.response.ParagraphResponse;
import com.poc.austin.response.ProductResult;
import com.poc.austin.response.User;
import com.poc.austin.service.FibonacciService;
import com.poc.austin.service.RestTemplateService;
import com.poc.austin.service.ServiceDeadlock;
import com.poc.austin.service.UniqueWordsService;

/**
 * Rest Controller for multiple end points 
 * @author Somnath
 *
 */
@RestController
public class RestfulServiceController {
	private Logger log = Logger.getLogger(RestfulServiceController.class);

	@Autowired
	UniqueWordsService wordservice;

	@Autowired
	FibonacciService fibonacciService;

	@Autowired
	RestTemplateService restservice;

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ServiceDeadlock serviceDeadlock;

	@Value("${external.service.url}")
	private String serviceUrl;

	
	/*** 
	 * String sample REST point for Hello World used for testing.
	 */

	@ApiOperation(value = "Hello World ", nickname = "Hello World  first rest call", httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
	ResponseEntity<String> welcome() throws Exception {
		return new ResponseEntity<String>("Hello World", HttpStatus.OK);
	}

	/*
	 * This API is to find unique words given a list of words. Accepts a JSON
	 * object containing a paragraph of text and returns a JSON Object
	 */
	@ApiOperation(value = "Paragraph", nickname = "Paragraph", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = ParagraphResponse.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/findUniqueWords", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ParagraphResponse findUniqueWords(
			@RequestBody Paragraph paragraphRequest) throws Exception {

		ParagraphResponse response = new ParagraphResponse();
		if (!paragraphRequest.getParagraphText().isEmpty()) {
			String line = paragraphRequest.getParagraphText().replace("\n"," ");
			response.setUniqueWords(wordservice.getUniqueWordsWithFrequency(line));
		}
		return response;
	}

	/*
	 * This API is accepts a number, N, and returns a JSON with the first N
	 * Fibonacci numbers using recursive method, but there is all a other
	 * process using Java 8 feature to the Fibonacci without recursive and much
	 * faster
	 */
	@RequestMapping(value = "/findFibonacci", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody FibonacciResponse findFibonacciNumber(
			@RequestBody Fibonacci numberRequest) throws Exception {

		FibonacciResponse fibonacciResponse = new FibonacciResponse();
		if (numberRequest.getnTH() != 0) {		
			fibonacciResponse.setFibonacciResult(fibonacciService
					.recursiveFibonacci(numberRequest.getnTH()));
		}
		return fibonacciResponse;

	}

	/* This REST API end point to consume 3rd party REST API and return back content */
	@RequestMapping(value = "/accessExternalCall", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<User> restCall() throws Exception {
		return restservice.externalWebServiceCall(serviceUrl);
	}
	
	
	/* REST end point to start thread which will go in deadloack stage for 30 seconds */
	@RequestMapping(value = "/create/deadlock", method = RequestMethod.GET)
	public ResponseEntity<String> createDeadLock() {
		log.debug("Threads to createing deadloack");
		serviceDeadlock.startThreadsDeadLock();
		log.debug("deadloacked thread started ");
		return new ResponseEntity<String>("Succefully started Thread", HttpStatus.OK);
	}
	
	/* REST end point for checking deadlock	 */
	@RequestMapping(value = "/check/deadlock", method = RequestMethod.GET)
	public ResponseEntity<String> checkDeadLock() {	
		return new ResponseEntity<String>(serviceDeadlock.checkDeadLock(), HttpStatus.OK);	
	}


	/* This API is use for finding all product from database(hsqldb)*/
	@RequestMapping(value = "/findAllProduct", method = RequestMethod.GET)
	public @ResponseBody Iterable<Product> findProduct(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// uses the findAll() method inherited from CrudRepository
		return productRepository.findAll();
	}

	/* This API is use for finding specified product from database(hsqldb)*/
	@ApiOperation(value = "Find Product by Id", nickname = "Product", httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = ParagraphResponse.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/findProduct/{id}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable("id") Integer id) {
		// uses the findOne() method inherited from CrudRepository
		return productRepository.findOne(id);
	}

	/* This API is use for finding specified productname and promotion code from database(hsqldb)*/
	@ApiOperation(value = "Query Product", nickname = "Product", httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = ParagraphResponse.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/queryProduct/{productname}/{promotion}", method = RequestMethod.GET)
	public List<Product> queryProduct(
			@PathVariable("productname") String productname,
			@PathVariable("promotion") Integer promotion) {
		// uses the findOne() method inherited from CrudRepository
		return productRepository.fetchProduct(productname, promotion);
	}

	/* This API is use delete product from database(hsqldb)*/
	@ApiOperation(value = "Delete Product", notes = "Deletes product by id", httpMethod = "DELETE")
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
	/* This API is use for adding product from database(hsqldb)*/
	@ApiOperation(value = "Addd Product", nickname = "Product", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = ProductResult.class),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProductResult addProduct(
			@RequestBody Product addProduct, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Saved Product - id: " + addProduct.getId());
		ProductResult productResult = new ProductResult();

		if (!productRepository.exists(addProduct.getId())) {
			productRepository.save(addProduct);

			productResult.setMessage("Inserted new record ");
			productResult.setId(addProduct.getId());
			return productResult;
		} else {
			productResult.setMessage("Record already exist:");
			productResult.setId(addProduct.getId());
			return productResult;
		}

	}	
	
	

	
}