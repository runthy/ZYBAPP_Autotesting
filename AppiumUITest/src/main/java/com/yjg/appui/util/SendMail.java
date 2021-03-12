package com.yjg.appui.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.yjg.appui.base.CrazyPath;


public class SendMail {   
    public void send(String title,String text,String[] to) {
        try {
           Properties p = new Properties(); //Properties p = System.getProperties();   
            p.put("mail.smtp.auth", "true");   
            p.put("mail.transport.protocol", "smtp");   
            p.put("mail.smtp.host", "smtp.163.com");   
            p.put("mail.smtp.port", "25");   
            //建立会话   
            Session session = Session.getInstance(p);   
            Message msg = new MimeMessage(session); //建立信息   
            ProUtil pu=new ProUtil(CrazyPath.globalPath);
            String sendUser=pu.getPro("senduser");
            String password=pu.getPro("password");
            msg.setFrom(new InternetAddress(sendUser)); //发件人   
            
            String toPersion = getMailList(to);
            InternetAddress[] iaToList = new InternetAddress().parse(toPersion);
            
            msg.setRecipients(Message.RecipientType.TO,iaToList); //收件人   
    
            msg.setSentDate(new Date()); // 发送日期   
            msg.setSubject(title); // 主题   
            msg.setText(text); //内容   
            // 邮件服务器进行验证   
            Transport tran = session.getTransport("smtp");   
            tran.connect("smtp.163.com",sendUser,password);    
            tran.sendMessage(msg, msg.getAllRecipients()); // 发送   
            System.out.println("邮件发送成功");   
    
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
    
    private String getMailList(String[] mailArray){
        
        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        if(mailArray!=null && length <2){
             toList.append(mailArray[0]);
        }else{
             for(int i=0;i<length;i++){
                     toList.append(mailArray[i]);
                     if(i!=(length-1)){
                         toList.append(",");
                     }
             }
         }
     return toList.toString();
    }
    public static void main(String[] args) {
    	SendMail sm=new SendMail();
    	ProUtil p=new ProUtil(CrazyPath.globalPath);
    	String[] to=p.getPro("tomail").split(",");
    	sm.send("第二期线下课","这是第二期线下课的邮件工具类",to);
	}


} 