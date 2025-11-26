package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import model.bean.Article;
import model.bo.ArticleBO;

@WebServlet("/welcome")
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ArticleBO articleBO;

    @Override
    public void init() {
        articleBO = new ArticleBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Article> articles = articleBO.getAllPublishedArticles();
        request.setAttribute("articles", articles);

        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }
}


