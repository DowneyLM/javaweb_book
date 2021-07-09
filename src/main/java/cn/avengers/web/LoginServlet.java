package cn.avengers.web;

import cn.avengers.pojo.User;
import cn.avengers.service.UserService;
import cn.avengers.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过action以后，废弃
 */
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //调用userService的login（）登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));

        //如果等于null，说明登录失败！
        if(loginUser == null){
            //把错误信息，和回显的表单项信息保存到Request域中
            req.setAttribute("msg","用户名或者密码错误！");
            req.setAttribute("username",username);
            //跳回登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }else {
            //登录成功
            //跳回到成功页面 login_success.html
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);

        }

    }
}
