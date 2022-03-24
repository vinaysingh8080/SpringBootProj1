package in.nit.service.impl;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.nit.model.Student;
import in.nit.repo.StudentRepo;
import in.nit.service.IStudentService;
@Service
public class StudentServiceImpl implements IStudentService {
	@Autowired
	private StudentRepo repo;
	@Override
	public Integer saveStd(Student student) {
		Integer id=repo.save(student).getId();
		return id;
	}
	@Override
	public Page<Student> fetchAll(Pageable pageable) {
		Page<Student> list=repo.findAll(pageable);
		return list;
	}
	@Override
	public void deleteStudent(Integer id) {
		repo.deleteById(id);
		
	}
	@Override
	public Student getOne(Integer id) {
		Student std=null;
		Optional<Student> opt=repo.findById(id);
		if(opt.isPresent()) {
			//avoid null pointer exception
			std=opt.get();
		}
		
		return std;
	}
	@Override
	public void deleteAllStudent() {
		repo.deleteAll();
		
	}
	@Override
	public List<Student> fetchAll() {
		List<Student> list=repo.findAll();
		return list;
	}
	@Override
	public void Updatestd(Student std) {
		repo.save(std);
		
	}

}
