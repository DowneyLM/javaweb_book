package cn.avengers.web;

import cn.avengers.pojo.User;
import cn.avengers.service.UserService;
import cn.avengers.service.impl.UserServiceImpl;
import cn.avengers.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet{

    private UserService userService = new UserServiceImpl();

    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        //获取请求的参数username
        String username = req.getParameter("username");
        //调用userService.existUsername();
        boolean existsUsername = userService.existsUsername(username);

        //把返回的结果封装称map对象
        HashMap<String, Object> resultMap = new HashMap<>();

        resultMap.put("existsUsername",existsUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);

        resp.getWriter().write(json);

    }

    /**
     * 注销
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        //1.销毁Session中用户登录的信息(或者销毁Session)
        req.getSession().invalidate();
        //2.重定向到首页（或者登录页面）
        resp.sendRedirect(req.getContextPath());

    }


    /**
     * 处理登录的功能
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //  1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 调用 userService.login()登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        // 如果等于null,说明登录 失败!
        if (loginUser == null) {
            // 把错误信息，和回显的表单项信息，保存到Request域中
            req.setAttribute("msg", "用户或密码错误！");
            req.setAttribute("username", username);
            //   跳回登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            // 登录 成功
            // 保存用户登录的信息到Session域中
            req.getSession().setAttribute("user", loginUser);
            //跳到成功页面login_success.html
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }


    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        //获取Session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除Session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());

        //2.检查验证码是否正确，写死，要求验证码是abcde
        if(token != null && token.equalsIgnoreCase(code)){
            //3.检查用户名是否可用
            if(userService.existsUsername(username)){
                System.out.println("用户名[" + username + "]已存在！");

                // 把回显信息，保存到Request域中
                req.setAttribute("msg", "用户名已存在！！");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

                //跳回注册页面
                req.getRequestDispatcher("/pages/user/register.jsp").forward(req,resp);
            }else {
                //调用Service保存到数据库中
                userService.registUser(new User(null,username,password,email));
                req.getSession().setAttribute("user",user);
                //跳到注册成功页面register_success.jsp
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        }else {
            //把回显信息，保存到Request域中
            // 把回显信息，保存到Request域中
            req.setAttribute("msg", "验证码错误！！");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            System.out.println("验证码[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

}
