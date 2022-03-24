package in.nit.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nit.model.Student;

public class StudentExcelDoc extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Define excel file name to be exported
		response.addHeader("Content-Disposition", "attachment:fileName=StudentData.xlsx");
		//read the data provided by controller
		@SuppressWarnings("unchecked")
		List<Student> list=(List<Student>) model.get("list");
		//create one sheet
		Sheet sheet=workbook.createSheet("Student");
		//create row as a header
		Row row0=sheet.createRow(0);
		row0.createCell(0).setCellValue("ID");
		row0.createCell(1).setCellValue("NAME");
		row0.createCell(2).setCellValue("FATHER NAME");
		row0.createCell(3).setCellValue("MOTHER NAME");
		row0.createCell(4).setCellValue("NUMBER");
		row0.createCell(5).setCellValue("EMAIL");
		row0.createCell(6).setCellValue("GENDER");
		row0.createCell(7).setCellValue("DATE OF BIRTH");
		row0.createCell(8).setCellValue("ADDRESS");
		//CREATE ROW ONE ONWARD FROM List<T>
		int rowNum=1;
		for(Student std:list) {
			Row row=sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(std.getId());
			row.createCell(1).setCellValue(std.getName());
			row.createCell(2).setCellValue(std.getFname());
			row.createCell(3).setCellValue(std.getMname());
			row.createCell(4).setCellValue(std.getNumber());
			row.createCell(5).setCellValue(std.getEmail());
			row.createCell(6).setCellValue(std.getGen());
			row.createCell(7).setCellValue(std.getDob());
			row.createCell(8).setCellValue(std.getAddr());
		}
	}

}
