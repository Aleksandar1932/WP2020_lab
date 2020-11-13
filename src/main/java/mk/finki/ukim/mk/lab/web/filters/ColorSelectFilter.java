//package mk.finki.ukim.mk.lab.web.filters;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(filterName = "ColorSelectFilter", urlPatterns = "/selectBalloon")
//public class ColorSelectFilter implements Filter {
//    public void destroy() {
//    }
//
//    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//
//
//        String color = request.getParameter("color");
//
//        if (color == null || color.isEmpty()) {
//            response.sendRedirect("/");
//        } else {
//
//            chain.doFilter(req, resp);
//        }
//    }
//
//    public void init(FilterConfig config) throws ServletException {
//
//    }
//
//}
