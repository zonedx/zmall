package com.zmall.controller.portal;

import com.zmall.common.Const;
import com.zmall.common.RedisPool;
import com.zmall.common.ServerResponse;
import com.zmall.controller.common.CurrentUser;
import com.zmall.controller.common.Login;
import com.zmall.pojo.User;
import com.zmall.service.IUserService;
import com.zmall.util.CookieUtil;
import com.zmall.util.JsonUtil;
import com.zmall.util.RedisPoolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: UserController
 * @Date 2019-09-15 01:53
 * @Author duanxin
 **/
@Api(tags = "user")
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    RedisPool redisPool;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {

        ServerResponse<User> response = iUserService.login(username, password);

        if (response.isSuccess()) {
            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
            RedisPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @Login
    @ApiOperation(value = "登出")
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpServletRequest request,HttpServletResponse response) {
        String loginToken = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request,response);
        RedisPoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "user对象", required = true, paramType = "query", dataType = "User")
    })
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    @ApiOperation(value = "校验用户名|邮箱是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str", value = "待校验数据", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "类型（username | email）", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @Login
    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(User user) {
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    @Login
    @ApiOperation(value = "遗忘密码获取问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.GET)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    @Login
    @ApiOperation(value = "遗忘密码检查问题答案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "question", value = "问题", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "answer", value = "答案", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    @Login
    @ApiOperation(value = "遗忘密码检查问题答案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "passwordNew", value = "新密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "forgetToken", value = "token，防止横向越权", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @Login
    @ApiOperation(value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "passwordOld", value = "旧密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "passwordNew", value = "新密码", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew) {
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    @Login
    @ApiOperation(value = "更新个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentUser", value = "当前登录用户", required = true, paramType = "query", dataType = "User"),
            @ApiImplicitParam(name = "user", value = "表单提交的user信息", required = true, paramType = "query", dataType = "User")
    })
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    public ServerResponse<User> updateInformation(HttpSession session,User currentUser,User user){
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @Login
    @ApiOperation(value = "获取个人信息")
    @RequestMapping(value = "get_information.do", method = RequestMethod.GET)
    public ServerResponse<User> getInformation(@CurrentUser User user){
        return iUserService.getInformation(user.getId());
    }
}
