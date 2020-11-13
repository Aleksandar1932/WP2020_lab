//package mk.finki.ukim.mk.lab.web.servlets;
//
//import mk.finki.ukim.mk.lab.model.Balloon;
//import mk.finki.ukim.mk.lab.service.BalloonService;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(name = "BalloonListServlet", urlPatterns = "/*")
//public class BalloonListServlet extends HttpServlet {
//    private final SpringTemplateEngine springTemplateEngine;
//    private final BalloonService balloonService;
//
//    // Constructor based dependency injection for the BalloonService
//    public BalloonListServlet(SpringTemplateEngine springTemplateEngine, BalloonService balloonService) {
//        this.springTemplateEngine = springTemplateEngine;
//        this.balloonService = balloonService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        WebContext context = new WebContext(req, resp, req.getServletContext());
//
//        List<Balloon> balloonList = balloonService.listAll();
//        context.setVariable("balloons", balloonList);
//
//        springTemplateEngine.process("listBalloons.html", context, resp.getWriter());
//    }
//}
