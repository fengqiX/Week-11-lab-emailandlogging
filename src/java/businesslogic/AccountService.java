/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;

/**
 *
 * @author awarsyle
 */
public class AccountService {

    public User checkLogin(String username, String password, String path) {
        User user;

        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);

            if (user.getPassword().equals(password)) {
                // successful login
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO,
                                "A user logged in: {0}", username);
                String email = user.getEmail();
                try {
                    // WebMailService.sendMail(email, "NotesKeepr Login", "Big brother is watching you!  Hi " + user.getFirstname(), false);

                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", ((new java.util.Date()).toString()));

                    try {
                        WebMailService.sendMail(email, "NotesKeepr Login", path + "/emailtemplates/login.html", contents);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }

                return user;
            }

        } catch (NotesDBException ex) {
        }

        return null;
    }

    public boolean resetPassword(String email, String path, String url) {
        UserService us = new UserService();
        try {
            String uuid = UUID.randomUUID().toString();
            String link = url + "?uuid=" + uuid;
            UserDB userDB = new UserDB();
            User user;
            user = userDB.getUserByEmail(email);
            if (user == null) {
                return false;
            }
            user.setResetPasswordUUID(uuid);
            us.update(user);
            HashMap<String, String> contents = new HashMap<>();
            contents.put("firstname", user.getFirstname());
            contents.put("lastname", user.getLastname());
            contents.put("username", user.getUsername());
            contents.put("link", link);

            WebMailService.sendMail(email, "NotesKeepr Password", path + "/emailtemplates/resetpassword.html", contents);
            return true;
        } catch (NotesDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) { 
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean changePassword(String uuid, String passowrd) {
        UserService us = new UserService();
        try {
            User user = us.getByUUID(uuid);
            if(user==null) return false;
            user.setPassword(passowrd);
            user.setResetPasswordUUID(null);
            us.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
}
