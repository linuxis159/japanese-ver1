package com.study.japanese.service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.study.japanese.dto.EmailMessageDto;
import com.study.japanese.entity.Code;
import com.study.japanese.entity.User;
import com.study.japanese.repository.CodeRedisRepository;
import com.study.japanese.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService{

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final CodeRedisRepository codeRedisRepository;

    public String ePw = "empty";


    public int alreadyJoinedEmailCheck(String email){
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty())
            return 0;
        else
            return 1;
    }

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("Japanese 인증번호가 도착했습니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요</h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("dlrrud147@naver.com","김익경"));//보내는 사람

        return message;
    }

    private MimeMessage createMessage(String to, EmailMessageDto messageDto)throws Exception{
            MimeMessage message = emailSender.createMimeMessage();
            message.addRecipients(RecipientType.TO, to);//보내는 대상
            message.setSubject(messageDto.getSubject());//제목

            String msgg = "";
            msgg += "<div style='margin:100px;'>";
            msgg += "<h1>안녕하세요 Japanese입니다</h1>";
            msgg += "<br>";
            msgg += "<div align='center' style='border:1px solid black; font-family:verdana; padding:100px';>";
            msgg += messageDto.getMessage();
            msgg += "</div>";
            msgg += "</div>";
            message.setText(msgg, "utf-8", "html");//내용
            message.setFrom(new InternetAddress("dlrrud147@naver.com", "김익경"));//보내는 사람
            return message;


    }
    //		인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    public void sendSimpleMessage(String to)throws Exception {
        ePw = createKey();
        Code code = new Code(ePw,1);
        codeRedisRepository.save(code);
        MimeMessage message = createMessage(to);;
        emailSender.send(message);


    }

    public void sendEmailMessages(List<String> userEmails, EmailMessageDto messageDto)throws Exception {
        // TODO Auto-generated method stub
        for(String to : userEmails) {
            MimeMessage message = createMessage(to, messageDto);
            emailSender.send(message);
        }


    }

}