package com.recipe.controllers;

import com.recipe.annotations.Injected;
import com.recipe.config.ServicesConfigurer;
import com.recipe.models.Commentary;
import com.recipe.models.User;
import com.recipe.services.CommentaryService;
import com.recipe.services.UserService;
import com.recipe.utils.CookiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/addCommentary")
public class CommentaryServlet extends HttpServlet {


    private UserService userService;

    private CommentaryService commentaryService;

    @Override
    public void init() throws ServletException {
        userService = ServicesConfigurer.getServicesConfig().getUserService();
        commentaryService = ServicesConfigurer.getServicesConfig().getCommentaryService();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = CookiesUtil.getAuthTokenEmail(req);
        User user = userService.findUserByEmail(email);

        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String stepIdString = req.getParameter("stepId");
        String commentaryText = req.getParameter("commentaryText");
        String orderNum = req.getParameter("orderNum");

        HttpSession session = req.getSession(false);

        if (stepIdString == null || stepIdString.isEmpty() || commentaryText == null || commentaryText.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Step ID and commentary text are required.");
            return;
        }

        Long stepId = null;
        Integer orderNumInt = null;
        try {
            stepId = Long.parseLong(stepIdString);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Step ID.");
            return;
        }

        try {
            orderNumInt = Integer.parseInt(orderNum);
        } catch (NumberFormatException e) {
        }

        Commentary commentary = new Commentary();
        commentary.setUserId(user.getId());
        commentary.setStepId(stepId);
        commentary.setText(commentaryText);

        boolean success;

        if (orderNumInt != null) {
            commentary.setOrderNum(orderNumInt);
            success = commentaryService.updateCommentary(commentary);
        } else success = commentaryService.addCommentary(commentary);

        if (success) {
            resp.sendRedirect("/recipe?id=" + session.getAttribute("recipeId"));
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save the commentary.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = CookiesUtil.getAuthTokenEmail(req);
        User user = userService.findUserByEmail(email);


        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String stepIdString = req.getParameter("stepId");

        Long stepId = null;
        try {
            stepId = Long.parseLong(stepIdString);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Step ID.");
            return;
        }

        String stepOrderNumString = req.getParameter("stepOrderNum");

        Integer stepOrderNum = null;
        try {
            stepOrderNum = Integer.parseInt(stepOrderNumString);
        } catch (NumberFormatException e) {
            return;
        }

        boolean success = commentaryService.deleteCommentary(user.getId(), stepId, stepOrderNum);

        if (success) {
            //resp.sendRedirect("recipeDetails.jsp?stepId=" + stepId);
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save the commentary.");
        }
    }

}
