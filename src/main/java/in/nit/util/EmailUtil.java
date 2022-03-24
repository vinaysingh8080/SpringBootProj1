package in.nit.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender sender;
	
	public boolean send(String to,String subject,String text) {
		boolean flag=false;
		try {
			//1.Create one MimeMessage Object
			MimeMessage message=sender.createMimeMessage();
			//2. Helper class object
			MimeMessageHelper helper=new MimeMessageHelper(message, flag);
			//3. setData
			helper.setTo(to);
			helper.setFrom("vinaysinghbhadauria95@gmail.com");
			helper.setSubject(subject);
			helper.setText(text);
			
			//4. send button
			sender.send(message);
			
		}catch(Exception r){r.printStackTrace();
		}
		return flag;
	}

}
