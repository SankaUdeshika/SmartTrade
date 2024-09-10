/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
@WebServlet(name = "Verification", urlPatterns = {"/Verification"})
public class Verification extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Workignbn1");

        Response_DTO response_DTO = new Response_DTO();
        System.out.println("Workignbn2");

        Gson gson = new Gson();
        JsonObject dto = gson.fromJson(req.getReader(), JsonObject.class);
        String Verification = dto.get("verification").getAsString();
        System.out.println("Workignbn3");

        if (req.getSession().getAttribute("email") != null) {
            System.out.println("Workignbn4");

            Session session = HibernateUtil.getSessionFactory().openSession();
            String email = req.getSession().getAttribute("email").toString();
            System.out.println("Workignbn5");

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", email));
            criteria.add(Restrictions.eq("verification", Verification));
            System.out.println("Workignbn6");

            if (!criteria.list().isEmpty()) {
                System.out.println("Workignbn7");

                User user = (User) criteria.list().get(0);
                user.setVerification("verified");
                session.update(user);
                session.beginTransaction().commit();
                System.out.println("Workignbn8");

                User_DTO userDTO = new User_DTO();
                userDTO.setFirst_name(user.getFirst_name());
                userDTO.setLast_name(user.getLast_name());
                userDTO.setEmail(user.getEmail());
                userDTO.setPassword(null);
                req.getSession().setAttribute("user", userDTO);
                System.out.println("Workignbn9");

                req.getSession().removeAttribute("email");
                req.getSession().setAttribute("user", user);
                response_DTO.setSuccess(true);
                response_DTO.setContent("Verification success");

            } else {
                response_DTO.setContent("Invalid verification code!");
            }
            session.close();

        } else {
            response_DTO.setContent("Verification unvailable Please Sign in");
        }
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response_DTO));
        System.out.println(gson.toJson(response_DTO));

    }

}
