package com.zmall.controller.backend;

import com.zmall.common.Const;
import com.zmall.common.ServerResponse;
import com.zmall.pojo.User;
import com.zmall.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName: UserManagerController
 * @Date 2019-09-15 13:28
 * @Author duanxin
 **/

@Api(tags = "uer-manager")
@Controller
@RequestMapping("/manager/user/")
public class UserManagerController {

    private IUserService iUserService;

    @Autowired
    public UserManagerController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if (response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
