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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sanka
 */
@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        User_DTO user_DTO = gson.fromJson(req.getReader(), User_DTO.class);

        if (user_DTO.getEmail().isEmpty()) {
            response_DTO.setContent("Please Enter Your Email");
        } else if (user_DTO.getPassword().isEmpty()) {
            response_DTO.setContent("Please enter Your password");
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", user_DTO.getEmail()));
            criteria.add(Restrictions.eq("password", user_DTO.getPassword()));

            if (!criteria.list().isEmpty()) {

                User user = (User) criteria.list().get(0);
                if (!user.getVerification().equals("verified")) {
//                    not Verified

                    req.getSession().setAttribute("user", user_DTO.getEmail());
                    response_DTO.setContent("Unverified");

                } else {
                    //  Verified
                    user_DTO.setFirst_name(user.getFirst_name());
                    user_DTO.setLast_name(user.getLast_name());
                    user_DTO.setEmail(user.getEmail());
                    user_DTO.setPassword(null);
                    req.getSession().setAttribute("user", user_DTO);

                    response_DTO.setSuccess(true);
                    response_DTO.setContent("SIGN In Suceess");
                }

            } else {
                response_DTO.setContent("Invalid Details, Please try again Later");
            }

        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response_DTO));
        System.out.println(gson.toJson(response_DTO));
    }

}
