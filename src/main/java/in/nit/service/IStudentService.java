package in.nit.service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.nit.model.Student;

public interface IStudentService {
	public Integer saveStd(Student student);
	public Page<Student> fetchAll(Pageable pageable);
	public void deleteStudent(Integer id);
	public Student getOne(Integer id);
	public void Updatestd(Student std);
	public void deleteAllStudent();
	public List<Student> fetchAll( );
}
