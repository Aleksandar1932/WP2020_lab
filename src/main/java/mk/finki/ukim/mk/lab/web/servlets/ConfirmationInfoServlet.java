package mk.finki.ukim.mk.lab.web.servlets;

import org.springframework.context.annotation.Profile;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Profile("servlet")
@WebServlet(name = "ConfirmationInfoServlet", urlPatterns = "/confirmationInfo")
public class ConfirmationInfoServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;

    public ConfirmationInfoServlet(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // This action is provided to test the session functionality when we try to access the Order Confirmation page from different tab using the same client
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("clientAgent", req.getHeader("User-Agent"));

        springTemplateEngine.process("confirmationInfo.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String clientName = req.getParameter("clientName");
        String deliveryAddress = req.getParameter("clientAddress");

        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("clientAgent", req.getHeader("User-Agent"));

        req.getSession().setAttribute("clientName", clientName);
        req.getSession().setAttribute("deliveryAddress", deliveryAddress);

//        "/orders/place?balloonColor=${session.color}?balloonSize=${session.balloonSize}?clientName=${session.clientName}?address=${session.deliveryAddress}"

        String balloonColor = (String) req.getSession().getAttribute("color");
        String balloonSize = (String) req.getSession().getAttribute("size");
//        req.getSession().setAttribute("balloon", new Order(balloonColor, balloonSize, clientName, deliveryAddress));

        springTemplateEngine.process("confirmationInfo.html", context, resp.getWriter());
    }
}
