/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.service;

import com.system.management.configs.EmailConfigs;
import com.system.management.configs.EncryptFactory;
import com.system.management.configs.UserLogsUtils;
import com.system.management.model.PasswordReset;
import com.system.management.model.User;
import com.system.management.objects.Response;
import com.system.management.repo.PasswordResetRepo;
import com.system.management.repo.UserRepo;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nauman
 */
@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetRepo resetRepo;

    @Autowired
    private EmailConfigs emailConfigs;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserLogsUtils logsUtils;
    

    public Response sendEmailForPasswordreset(Long resetId, String  adminName) {

        Optional<User> adminUser = userRepo.findByUserName(adminName);
        System.out.println("Admin Name:"+adminUser.get().getUserName());
        if (adminUser.isPresent()) {
            Optional<PasswordReset> resetUser = resetRepo.findById(resetId);
            System.out.println("Reset User name:"+resetUser.get().getUsername());
            if(resetUser.isPresent())
            {
                System.out.println("username:"+resetUser.get().getUsername());
            Optional<User> actualUser=userRepo.findByUserName(resetUser.get().getUsername());
            if(actualUser.isPresent())
            {
                System.out.println("Actual user:"+actualUser.get().getUserName());
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(emailConfigs.getHost());
            mailSender.setPort(emailConfigs.getPort());
            mailSender.setUsername(emailConfigs.getUsername());
            mailSender.setPassword(emailConfigs.getPassword());
                String newPassword = getAlphaNumericString(8);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(adminUser.get().getEmail());
                mailMessage.setTo(resetUser.get().getEmail());
                mailMessage.setSubject("Password Reset Response");
                mailMessage.setText("Your New Passoword Is:" + newPassword);
                String encPass=EncryptFactory.decryptData(newPassword);
                mailSender.send(mailMessage);
                actualUser.get().setPassword(encPass);
                userRepo.save(actualUser.get());
                resetRepo.delete(resetUser.get());
                logsUtils.saveUserLog(adminName, "RESET PASSWORD FOR USER :"+resetUser.get().getUsername());
                return new Response("0000", "Reset Password Successfull",null);
            }
            return new Response("1001", "reset Failed", null);
            }
            return new Response("1001", "reset Failed", null);
        }
        else 
            return new Response("1001", "reset Failed", null);

    }

    public Response submitPasswordResetRequest(String username) {
        Optional<PasswordReset> password = resetRepo.findByUsername(username);
        if (password.isPresent()) {
            return new Response("1111", null, null);
        }

        Optional<User> user = userRepo.findByUserName(username);
        if (user.isPresent()) {
            PasswordReset passwordReset = new PasswordReset();
            passwordReset.setUsername(username);
            passwordReset.setCompany(user.get().getCompany().getName());
            passwordReset.setEmail(user.get().getEmail());
            passwordReset.setUserId(user.get().getId());
            resetRepo.save(passwordReset);
            return new Response("0000", null, null);
        } else {
            return new Response("1001", null, null);
        }
    }
    public Page<PasswordReset> getPasswordResetRequest(int pageNo,int pageSize,String username)
    {
         Pageable pageable=PageRequest.of(pageNo, pageSize);
        Optional<User> user=userRepo.findByUserName(username);
        if(user.isPresent())
        {
            if(user.get().getRole().getName().equals("SUPER_ADMIN"))
            {
                return resetRepo.findAll(pageable);
            }
            else
            {
                String companyName=user.get().getCompany().getName();
                return resetRepo.findAllByCompany(companyName, pageable);
            }
        }
        else 
            return null;
       
        
    }
    private String getAlphaNumericString(int n) {

        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        StringBuffer r = new StringBuffer();

        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        return r.toString();
    }
}
