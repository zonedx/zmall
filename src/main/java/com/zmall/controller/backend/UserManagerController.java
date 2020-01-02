package com.zmall.controller.backend;

import com.zmall.common.Const;
import com.zmall.common.ServerResponse;
import com.zmall.pojo.User;
import com.zmall.service.IUserService;
import com.zmall.util.CookieUtils;
import com.zmall.util.JsonUtils;
import com.zmall.util.RedisPoolUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: UserManagerController
 * @Date 2019-09-15 13:28
 * @Author duanxin
 **/

@Api(tags = "uer-manager")
@Controller
@RequestMapping("/manage/user/")
public class UserManagerController {

    private IUserService iUserService;

    @Autowired
    public UserManagerController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> response = iUserService.login(username,password);
        if (response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                CookieUtils.writeLoginToken(httpServletResponse,session.getId());
                RedisPoolUtils.setEx(session.getId(), JsonUtils.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
