/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import entity.Category;
import entity.Color;
import entity.Product_Condition;
import entity.Model;
import entity.Storage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author sanka
 */
@WebServlet(name = "LoadFeatures", urlPatterns = {"/LoadFeatures"})
public class LoadFeatures extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Response_DTO response_DTO = new Response_DTO();
        Gson gson = new Gson();

        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria1 = session.createCriteria(Category.class);
        criteria1.addOrder(Order.asc("name"));
        List<Category> categoryList = criteria1.list();

        Criteria criteria2 = session.createCriteria(Model.class);
        criteria2.addOrder(Order.asc("name"));
        List<Model> modelList = criteria2.list();

        Criteria criteria3 = session.createCriteria(Color.class);
        criteria3.addOrder(Order.asc("name"));
        List<Color> ColorList = criteria3.list();

        Criteria criteria4 = session.createCriteria(Storage.class);
        criteria4.addOrder(Order.asc("id"));
        List<Storage> StorageList = criteria4.list();

        Criteria criteria5 = session.createCriteria(Product_Condition.class);
        criteria5.addOrder(Order.asc("name"));
        List<Product_Condition> ConditionList = criteria5.list();
        System.out.println("LoadWorking");

        JsonObject josObject = new JsonObject();
        josObject.add("categoryList", gson.toJsonTree(categoryList));
        josObject.add("modelList", gson.toJsonTree(modelList));
        josObject.add("colorList", gson.toJsonTree(ColorList));
        josObject.add("storageList", gson.toJsonTree(StorageList));
        josObject.add("productCondition", gson.toJsonTree(ConditionList));

//        send response
        resp.setContentType("applicaiton/json");
        resp.getWriter().write(gson.toJson(josObject));
        session.close();
    }

}
