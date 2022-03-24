package in.nit.controller;


import java.time.LocalTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.Student;
import in.nit.service.IStudentService;
import in.nit.util.EmailUtil;
import in.nit.view.StudentExcelDoc;
import in.nit.view.StudentPdf;

@Controller
@RequestMapping("/student")
public class EmployeeController {
	private static Logger logger=Logger.getLogger(EmployeeController.class);
	
	@Autowired
	private IStudentService service;
	@Autowired
	private EmailUtil util;
	
	
	@RequestMapping("/show")	
	public String showPage(Model model) {
		//form backing object
		model.addAttribute("student",new Student());
		return "Student";
	}
	
	@RequestMapping("/save")
	public String saveStd(@ModelAttribute Student student,
			Model model) {
		logger.info("Enter into Method");
		try {
			Integer id=service.saveStd(student);
			logger.debug("Student Saved Id is :"+id);
			LocalTime myObj = LocalTime.now();
			String sub="Registration Sucessfully....";
			String body="Registration confrimed  Hi "+student.getName()+
					", Thank you for Registration and create   account "
					+ "in Delhi public school."+ myObj;
			util.send(student.getEmail(), sub, body);
			logger.debug("Email Successfuly sent:"+student.getEmail());
			String Message="Student'"+id+"' Registration Successfully";
			model.addAttribute("message", Message);
			logger.info("Form backing object about to set");
			model.addAttribute("student",new Student());
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Unable to save student"+e.getMessage());
		}
		logger.info("Moving to UI to render message" );
		return "Student"; 
	}
	@RequestMapping("/fetch")
	public String fetchAll(@PageableDefault(size=3) Pageable pageable,Model model) {
		logger.info("Enter into fetch method");
		try {
			Page<Student> page=service.fetchAll(pageable);
			logger.debug("Student details fetched Successfully");
			model.addAttribute("list", page.getContent());
			model.addAttribute("page", page);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Unable to fetch the student details"+e.getMessage());
		}
		logger.info("Back to Student Data Page");
		return "StudentData";
	}
	@RequestMapping("/delete/{id}")
	public String deleteStudent(
			@PathVariable("id") Integer id,Pageable pageable,Model model)
	
	{
		logger.info("Enter in to delete method");
		try {
			service.deleteStudent(id);
			logger.debug("Student id:"+id+"Deleted Successfully....");
			model.addAttribute("message", "Student '"+id+"' deleted");
			//fetch new Data
			Page<Student> page=service.fetchAll(pageable);
			logger.debug("Fetch new Data Sucessfully..");
			model.addAttribute("list", page.getContent());
			model.addAttribute("page", page);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Unable to  delete data"+e.getMessage());
		}
		logger.info("Render to Student Data");
		return "StudentData";
		
	}
	@RequestMapping("/one/{id}")
	public String showOneData(@PathVariable("id") 
	Integer id,Model model) {
		logger.info("Enter into fetch only specific method");
		try {
			Student std=service.getOne(id);
			logger.debug("fetch Data Successfully...");
			model.addAttribute("std",std);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Unable to fetch Single data"+e.getMessage());
		}
		logger.info("Back to render Page");
		return "OneStudentData";
	}
	@RequestMapping("/edit/{id}")
	public String editStd(@PathVariable("id") Integer id, Model model) {
		Student st=service.getOne(id);
		model.addAttribute("std", st);
		return "StudentEdit";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String updateStd(@ModelAttribute Student std,Model model) {
		service.Updatestd(std);
		System.out.println(std.getId());
		return "redirect:../student/fetch";
	}
	
	
	@RequestMapping("/delete/all")
	public String deleteAll(Model model,Pageable pageable) {
		logger.info("Enter into Delete all method");
		try {
			service.deleteAllStudent();
			logger.debug("Delete All Data Sucessfully");
			//fetch new Data
			Page<Student> page=service.fetchAll(pageable);
			logger.debug("fetch new data");
			model.addAttribute("list", page.getContent());
			model.addAttribute("page", page);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Unable to delete all data"+e.getMessage());
		}
	
		logger.info("back to render page");
		return "StudentData";
	}
	@RequestMapping("/excelExport")
	public ModelAndView exportToExcel() {
		logger.info("Enter into excel method");
		ModelAndView mav=null;
		try {
			  mav=new ModelAndView();
			mav.setView(new StudentExcelDoc());
			//read data from DB
			List<Student> list=service.fetchAll();
			logger.debug("fetch data from Db");
			// send data to excel class
			mav.addObject("list",list);
			logger.debug("Send data to Excel class");
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Unable to download excel data"+e.getMessage());
		}
		logger.info("back to madel and view");
		return mav;
	}
	@RequestMapping("/pdfExport")
	public ModelAndView exportTopdf() {
		ModelAndView mav=null;
		try {
			logger.info("Enter in to pdf method");
			 mav=new ModelAndView();
				mav.setView(new StudentPdf());
				//read data from DB
				List<Student> list=service.fetchAll();
				logger.debug("Fetch all Data from Db " );
				// send data to excel class
				mav.addObject("list",list);
				logger.debug("All Data send to Excel class");
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Unable download pdf data");
		}
		logger.info("back to model and view");
		return mav;
	}
	
}
