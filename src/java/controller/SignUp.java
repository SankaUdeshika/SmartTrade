/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.ResponseAPDU;
import model.HibernateUtil;
import model.Mail;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Response_DTO response_DTO = new Response_DTO(); // mekata thama Data tika mulinma danne

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); // password eke get eka access nathi karala anith ewage access dena GSON object eka

        User_DTO user_dto = gson.fromJson(request.getReader(), User_DTO.class); // request eke ena Data tika Assign karagannawa DTO ekata

//        Validation
        if (user_dto.getFirst_name().isEmpty()) {
            response_DTO.setContent("Please Enter Your First Name");

        } else if (user_dto.getLast_name().isEmpty()) {
            response_DTO.setContent("Please Enter Your Last Name");
        } else if (user_dto.getEmail().isEmpty()) {
            response_DTO.setContent("Please Enter Your Email");
        } else if (!Validation.isEmailValid(user_dto.getEmail())) { // condition true unoth block eka wada karanne
            response_DTO.setContent("Please Enter Your Valid Email");
        } else if (user_dto.getPassword().isEmpty()) {
            response_DTO.setContent("Please Enter Your password ");
        } else if (!Validation.ispasswordValid(user_dto.getPassword())) {
            response_DTO.setContent("Please Enter Valid password (password must "
                    + "include at least one uppercase letter, number, special "
                    + "charachter and 8 charachters) ");
        } else {

//            Search is User already registerd?
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", user_dto.getEmail()));

            if (!criteria.list().isEmpty()) {
                response_DTO.setContent("User with this Email already Exists");
            } else {

//                Generate Verification Code
                int code = (int) (Math.random() * 1000000);

//                USer Entity ekata DTO eke dewl tika dagena yanawa.
                final User user = new User();
                user.setEmail(user_dto.getEmail());
                user.setFirst_name(user_dto.getFirst_name());
                user.setLast_name(user_dto.getLast_name());
                user.setPassword(user_dto.getPassword());
                user.setVerification(String.valueOf(code));

//                send Verification Mail
                Thread sendMailThread = new Thread() {
                    @Override
                    public void run() {
                        Mail.sendMail(user_dto.getEmail(), "Smart Trade Verification", "<h1 style =\"color:#6482AD\"> Your Verificaiton Code :" + user.getVerification() + "</h1>");
                    }
                };
//                sendMailThread.start(); // Mail Thread eka Start karagannawa.

                session.save(user); // hadapu session ekata User wa damma
                session.beginTransaction().commit();// Session eka Database ekata Insert eka kara.
                System.out.println("9");

//                Response eke thiyena  Details fill karanawa
                response_DTO.setSuccess(true);
                response_DTO.setContent("Registration Complete, Please Verify your acoount to sign in");

            }
            session.close(); // close Session
        }

        response.setContentType("application/json"); // Content Type eka dannama oni JS eken Allaganna
        response.getWriter().write(gson.toJson(response_DTO)); // response eka widihata responseDTO eka json Object ekak widiahata hdala danawa.
        System.out.println(gson.toJson(response_DTO));

    }

}
