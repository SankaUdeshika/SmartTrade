/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import entity.Category;
import entity.Color;
import entity.Model;
import entity.Product_Condition;
import entity.Storage;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validation;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author sanka
 */
@MultipartConfig
@WebServlet(name = "ProductListing", urlPatterns = {"/ProductListing"})
public class ProductListing extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Gson gson = new Gson();

        String categoryId = request.getParameter("categoryId");
        String modelId = request.getParameter("modelId");
        String title = request.getParameter("title");
        String descripiton = request.getParameter("descripiton");
        String storageId = request.getParameter("storageId");
        String colorId = request.getParameter("colorId");
        String ConditionId = request.getParameter("ConditionId");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");

        Part image1 = request.getPart("image1");
        Part image2 = request.getPart("image2");
        Part image3 = request.getPart("image3");

        Response_DTO response_DTO = new Response_DTO();

        if (title.isEmpty()) {
            response_DTO.setContent("Please Fill Product Title");
        } else if (descripiton.isEmpty()) {
            response_DTO.setContent("Please Fill Product descritption");
        } else if (!Validation.isDouble(price)) {
            response_DTO.setContent("Please Fill Valid Price");
        } else if (quantity.isEmpty()) {
            response_DTO.setContent("Please Fill Quantity");
        } else if (!Validation.isInteger(quantity)) {
            response_DTO.setContent("Please Enter Valid Quanitity");
        } else if (Integer.parseInt(quantity) <= 0) {
            response_DTO.setContent("Please Enter Valid Quanitity");
        } else if (Integer.parseInt(price) <= 0) {
            response_DTO.setContent("Please Enter Valid Quanitity");
        } else if (image1.getSubmittedFileName() == null) {
            response_DTO.setContent("Please Upload Image 1");
        } else if (image2.getSubmittedFileName() == null) {
            response_DTO.setContent("Please Upload Image 2");
        } else if (image3.getSubmittedFileName() == null) {
            response_DTO.setContent("Please Upload Image 3");
        } else {
            Category category = (Category) session.get(Category.class, Integer.parseInt(categoryId));

            if (category == null) {
                response_DTO.setContent("Please Select Valid  Category");
            } else {
                Model model = (Model) session.get(Model.class, Integer.parseInt(modelId));
                if (model == null) {
                    response_DTO.setContent("Please Select Valid  Model");

                } else {
                    if (model.getCategory().getId() != category.getId()) {
                        response_DTO.setContent("Please Select Valid  Model");
                    } else {

                        Storage storage = (Storage) session.get(Storage.class, Integer.parseInt(storageId));

                        if (storage == null) {
                            response_DTO.setContent("please Select valid Storage");
                        } else {
                            Color color = (Color) session.get(Color.class, Integer.parseInt(colorId));
                            if (color == null) {
                                response_DTO.setContent("please Select a Valid Color ");
                            } else {
                                Product_Condition condition = (Product_Condition) session.get(Product_Condition.class, Integer.parseInt(ConditionId));
                                if (condition == null) {
                                    response_DTO.setContent("Please select a valid Condition");
                                } else {
                                    response_DTO.setContent("DOne");

                                }
                            }
                        }

                    }
                }
            }

        }

        response.setContentType("applicaiton/json");
        response.getWriter().write(gson.toJson(response_DTO));

    }

}
