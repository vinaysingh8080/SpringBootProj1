package in.nit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nit.model.Student;
import in.nit.service.IStudentService;
import in.nit.util.EmailUtil;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentRestController {
	@Autowired
	private EmailUtil util;
	@Autowired
	private IStudentService service;
	
	@PostMapping("/save/data")
	public ResponseEntity<String> add(@RequestBody Student student) {
		ResponseEntity<String> resp=null;
		try {
			Integer id=service.saveStd(student);
			String to=student.getEmail();
			String sub="Thank You For Registration....";
			String text="Hii"+student.getName()+"Welcome to delhi public school";
			util.send(to, sub, text);
			resp=new ResponseEntity<String>("Student"+id+"created",HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
			resp=new ResponseEntity<String>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resp;
	}
	@GetMapping("/all")
	public ResponseEntity<?> allStudent(){
		ResponseEntity<?> resp=null;
		try {
			List<Student> list=service.fetchAll();
			resp=new ResponseEntity<List<Student>>(list,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			resp=new ResponseEntity<String>
			("Unable to fetch",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") Integer id){
		ResponseEntity<String> resp=null;
		try {
			service.deleteStudent(id);
			resp=new ResponseEntity<String>("Student"+ id+"deleted",HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			resp=new ResponseEntity<String>("Unable to delete ",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	
	

}
