package com.btw.account;

import com.btw.account.annotations.Authentication;
import com.btw.account.domain.IdentityType;
import com.btw.account.domain.User;
import com.btw.account.domain.UserAuthorize;
import com.btw.account.requestmodel.*;
import com.btw.account.domain.UserAuthorizeService;
import com.btw.account.domain.UserService;
import com.btw.account.thirdpart.sharesdk.SmsWebApiKit;
import com.btw.response.ResponseEntity;
import com.btw.response.ResponseCode;
import com.btw.utils.MD5Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by gplzh on 2016/5/29.
 */

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String MD5_PASSWORD_SALT = "&*(*@H()!3@#jk*@#hod";

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthorizeService userAuthorizeService;

    @Value("${sys.file.user.avatar.path}")
    private String userAvatarPath;

    /**
     * 手机注册
     *
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/registerByPhone", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "手机注册", response = ResponseEntity.class)
    @ResponseBody
    public ResponseEntity registerByPhone(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        ResponseEntity response = new ResponseEntity();

        UserAuthorize authorize = userAuthorizeService.findByIdentifier(request.phone);
        if (authorize != null) {
            response.setCode(ResponseCode.ALREADY_AUTHORIZE);
            return response;
        }

        try {
//            SmsWebApiKit apiKit = new SmsWebApiKit();
//            String result = apiKit.checkcode(request.phone, request.zone, request.code);
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String, Integer> map = mapper.readValue(result, Map.class);
//            if (map.get("status") == 200) {
            User user = createUser(IdentityType.PHONE, request.phone, request.credential, request.nickname, request.avatar);
            response.setResult(user);
//            }
//            else{
//                response.setResult(map.get("status"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }

        return response;
    }

    /**
     * 第三方授权注册
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "授权", response = RegisterResponse.class)
    @ResponseBody
    public RegisterResponse registerByThirdParty(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult, @ApiIgnore HttpSession session) {
        RegisterResponse response = new RegisterResponse();

        UserAuthorize authorize = userAuthorizeService.findByIdentityTypeAndIdentifierAndCredential(
                request.identityType, request.identifier, MD5Helper.createMd5(request.credential, MD5_PASSWORD_SALT));
        if (authorize == null) {

            User user = createUser(request.identityType, request.identifier, request.credential, request.nickname, request.avatar);
            session.setAttribute("userInfo", user);
        } else {
            response.setCode(ResponseCode.ALREADY_AUTHORIZE);
        }

        return response;
    }

    /**
     * 登陆，可以是手机登录，可以是第三方登录
     *
     * @param request
     * @param session
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "登陆", response = LoginResponse.class)
    @ResponseBody
    public LoginResponse login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult, @ApiIgnore HttpSession session) {
        LoginResponse response = new LoginResponse();

        UserAuthorize authorize = userAuthorizeService.findByIdentityTypeAndIdentifierAndCredential(
                request.identityType, request.identifier, MD5Helper.createMd5(request.credential, MD5_PASSWORD_SALT));
        if (authorize == null) {
            response.setCode(ResponseCode.NO_AUTHORIZE);
        } else {
            User user = userService.findById(authorize.getUserId());
            response.setResult(user);
            session.setAttribute("userInfo", user);
        }

        return response;
    }

    /**
     * 修改个人资料
     *
     * @param request
     * @param session
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/modifyProfile", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "修改个人资料", response = ModifyProfileResponse.class)
    @ResponseBody
    @Authentication
    public ModifyProfileResponse modifyProfile(@Valid @RequestBody ModifyProfileRequest request, BindingResult bindingResult, @ApiIgnore HttpSession session) {
        ModifyProfileResponse response = new ModifyProfileResponse();

        User user = (User) session.getAttribute("userInfo");
        user.setNickname(request.nickname);
        user.setAvatar(request.avatar);
        user.setSex(request.sex);
        userService.save(user);
        response.setResult(user);

        return response;
    }

    /**
     * 修改密码
     *
     * @param request
     * @param session
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "修改密码", response = ResponseEntity.class)
    @ResponseBody
    @Authentication
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordRequest request, BindingResult bindingResult, @ApiIgnore HttpSession session) {
        ResponseEntity response = new ResponseEntity();

        User user = (User) session.getAttribute("userInfo");
        UserAuthorize authorize = userAuthorizeService.findByUserId(user.getId());
        if (authorize.getCredential().equals(MD5Helper.createMd5(request.oldPassword, MD5_PASSWORD_SALT))) {
            authorize.setCredential(MD5Helper.createMd5(request.newPassword, MD5_PASSWORD_SALT));
            userAuthorizeService.save(authorize);
        } else {
            response.setCode(ResponseCode.PASSWORD_ERROR);
        }

        return response;
    }

    /**
     * 重置密码
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "重置密码", response = ResponseEntity.class)
    @ResponseBody
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequest request, BindingResult bindingResult) {
        ResponseEntity response = new ResponseEntity();

        UserAuthorize authorize = userAuthorizeService.findByIdentifier(request.phone);
        if (authorize == null) {
            response.setCode(ResponseCode.NO_AUTHORIZE);
            return response;
        }

        try {
            SmsWebApiKit apiKit = new SmsWebApiKit();
            String result = apiKit.checkcode(request.phone, request.zone, request.code);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Integer> map = mapper.readValue(result, Map.class);
            if (map.get("status") == 200) {
                authorize.setCredential(MD5Helper.createMd5(request.newPassword, MD5_PASSWORD_SALT));
                userAuthorizeService.save(authorize);
            } else {
                response.setResult(map.get("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }

        return response;
    }

    /**
     * 上传头像
     * @param file
     * @param session
     * @return
     */
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "上传头像", response = ResponseEntity.class)
    @ResponseBody
    @Authentication
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile file, @ApiIgnore HttpSession session) {
        ResponseEntity responseEntity = new RegisterResponse();
        if (!file.isEmpty()) {
            try {
                if (file.getSize() > 102400) {
                    responseEntity.setCode(ResponseCode.FILE_LIMIT_SIZE_100KB);
                    return responseEntity;
                }
                if (file.getContentType().toLowerCase().contains("png") || file.getContentType().toLowerCase().contains("jpg")) {
//                User user = (User) session.getAttribute("userInfo");
//                File desc = makefile(userAvatarPath + user.getId());
                    File desc = makefile(userAvatarPath + file.getOriginalFilename());
                    file.transferTo(desc);

//                if(user.getAvatar() != null){
//                    user.setAvatar(String.valueOf(user.getId()));
//                    userService.save(user);
//                }
                    return responseEntity;
                }
                else{
                    responseEntity.setCode(ResponseCode.FILE_EXTENSION_ERROR_PNG_JPG);
                    return responseEntity;
                }
            } catch (Exception e) {
                e.printStackTrace();
                responseEntity.setCode(ResponseCode.UNKNOWN_ERROR);
                return responseEntity;
            }
        } else {
            responseEntity.setCode(ResponseCode.EMPTY_FILE);
            return responseEntity;
        }
    }

    /**
     * 创建目录和文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    private File makefile(String path) throws IOException {

        if (path == null || "".equals(path.trim()))
            return null;
        String dirPath = path.substring(0, path.lastIndexOf("\\"));
        int index = path.lastIndexOf(".");
        if (index > 0) { // 全路径，保存文件后缀
            File dir = new File(dirPath);
            if (!dir.exists()) { //先建目录
                dir.mkdirs();
                dir = null;
            }

            File file = new File(path);
            if (!file.exists()) {//再建文件
                file.createNewFile();
            }
            return file;
        } else {
            File dir = new File(dirPath); //直接建目录
            if (!dir.exists()) {
                dir.mkdirs();
                dir = null;
            }
            return dir;
        }
    }

    /**
     * create user
     *
     * @param identityType
     * @param identifier
     * @param credential
     * @param nickname
     * @param avatar
     * @return
     */
    private User createUser(int identityType, String identifier, String credential, String nickname, String avatar) {

        User user = new User();
        user.setNickname(nickname);
        user.setAvatar(avatar);
        userService.save(user);

        UserAuthorize authorize = new UserAuthorize();
        authorize.setUserId(user.getId());
        authorize.setIdentityType(identityType);
        authorize.setIdentifier(identifier);
        authorize.setCredential(MD5Helper.createMd5(credential, MD5_PASSWORD_SALT));
        authorize.setVerified(true);
        userAuthorizeService.save(authorize);

        return user;
    }


    class LoginResponse extends ResponseEntity<User> {
    }

    class RegisterResponse extends ResponseEntity<User> {
    }

    class ModifyProfileResponse extends ResponseEntity<User> {
    }
}
