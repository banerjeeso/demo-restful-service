package com.cromecast.controller;

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

import com.cromecast.domain.Student;
import com.cromecast.model.Fibonacci;
import com.cromecast.model.Paragraph;
import com.cromecast.model.User;
import com.cromecast.repositories.StudentRepository;
import com.cromecast.response.FibonacciResponse;
import com.cromecast.response.ParagraphResponse;
import com.cromecast.response.RestTemplateService;
import com.cromecast.service.FibonacciService;
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
	StudentRepository studentRepository;
	
	@Value("${external.service.url}")
	private String serviceUrl;
	
	@RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
	ResponseEntity<String> welcome() throws Exception {
		return new ResponseEntity<String>("Hello World", HttpStatus.OK);
	}

	@RequestMapping(value = "/findUniqueWords", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ParagraphResponse findUniqueWords(
			@RequestBody Paragraph paragraphRequest	)
			throws Exception {
		
		ParagraphResponse response= new ParagraphResponse();
		if (!paragraphRequest.getParagraphText().isEmpty()){
			String line[] = paragraphRequest.getParagraphText().split("\n");
			response.setUniqueWords(wordservice.getFrequency(line));
		}
		return response;

	}
	
	
	@RequestMapping(value = "/findFibonacci", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody FibonacciResponse findFibonacciNumber(
			@RequestBody Fibonacci numberRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		FibonacciResponse fibonacciResponse = new FibonacciResponse();
		if (numberRequest.getFibonacciNumbers() != 0) {
			List<Integer> result=fibonacciService.generate(numberRequest.getFibonacciNumbers());
			fibonacciResponse.setFibonacciResult(result);		

		}
		return fibonacciResponse;

	}	
	
	@RequestMapping(value = "/restCall", 
			method = RequestMethod.GET, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public @ResponseBody List<User> restCall(HttpServletRequest request, HttpServletResponse response)
			throws Exception {			
			return restBuilder.externalWebServiceCall(User.class,serviceUrl);		
	}

	
	@RequestMapping(value = "/findAllStudent", 
			method = RequestMethod.GET)
	
	public @ResponseBody Iterable<Student> findStudent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
				return studentRepository.findAll();				
	}
	
	
	@RequestMapping(value = "/findStudent/{id}")
    public Student getStudent(@PathVariable("id") Integer id) {
        return studentRepository.findOne(id);// uses the findOne() method inherited from CrudRepository
    }
	
	 @RequestMapping(value = "/removeStudent/{id}", method = RequestMethod.DELETE)
	    public String deleteBook(@PathVariable("id") Integer id) {
	 
	        try {
	        	studentRepository.delete(id);
	            return String.format("Student [%s] remove from class successfully", id);// uses the delete() method inherited from CrudRepository
	        } catch (Exception e) {
	            return String.format("A problem occurred when deleting Student [%s]", e.getMessage());
	        }
	    }
	 
	
	 @RequestMapping(value = "/addStudent", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE, 
				produces = MediaType.APPLICATION_JSON_VALUE)
		
	public @ResponseBody Student addStudent(@RequestBody Student addStudent,HttpServletRequest request, HttpServletResponse response)
				throws Exception {	
		 
		 Student newStudent =studentRepository.save(addStudent);
	     log.info("Saved First garde  - id: " + addStudent.getId());
	return newStudent;				
		}
	 
}