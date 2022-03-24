package in.nit.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;

import com.lowagie.text.pdf.PdfWriter;

import in.nit.model.Student;

public class StudentPdf extends AbstractPdfView{

	protected void buildPdfMetaDeta(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request)  {
		Font headerFont=new Font(Font.TIMES_ROMAN, 20, Font.BOLD,Color.magenta);
		HeaderFooter header=new HeaderFooter(new Phrase("All Dtudent Data",headerFont), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
		Font dateFont =new Font(Font.HELVETICA,12, Font.NORMAL,Color.BLUE);
		HeaderFooter footer=new HeaderFooter(new Phrase("Pdf Export Executed On :"+new Date(),dateFont),true);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.setFooter(footer);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Download Pdf with a given filename
		response.addHeader("Content-Disposition", "attachment; filename=student.pdf");
		//read data from controller
		@SuppressWarnings("unchecked")
		List<Student> list=(List<Student>) model.get("list");
		//create Element
		Font titleFont =new Font(Font.TIMES_ROMAN,24,Font.BOLD,Color.blue);
		Paragraph title=new Paragraph("All Student Data",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(20.0f);
		title.setSpacingAfter(25.0f);
		
		//add to document
		document.add(title);
		Font tableHead=new Font(Font.TIMES_ROMAN,12,Font.BOLD,Color.blue);
		PdfPTable table=new PdfPTable(9);//no. of columns
		table.addCell(new Phrase("ID",tableHead));
		table.addCell(new Phrase("Name",tableHead));
		table.addCell(new Phrase("Father's Name",tableHead));
		table.addCell(new Phrase("Mother's Name",tableHead));
		table.addCell(new Phrase("Number",tableHead));
		table.addCell(new Phrase("Email",tableHead));
		table.addCell(new Phrase("Gender",tableHead));
		table.addCell(new Phrase("DOB",tableHead));
		table.addCell(new Phrase("Address",tableHead));
		for(Student std:list) {
			table.addCell(std.getId().toString());
			table.addCell(std.getName());
			table.addCell(std.getFname());
			table.addCell(std.getMname());
			table.addCell(std.getNumber());
			table.addCell(std.getEmail());
			table.addCell(std.getGen());
			table.addCell(std.getDob());
			table.addCell(std.getAddr());
			
			
		}
		document.add(table);
	}


}
