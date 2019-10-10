package life.lxj.community.controller;

import life.lxj.community.dto.AccessTokenDTO;
import life.lxj.community.dto.GithubUser;
import life.lxj.community.mapper.UserMapper;
import life.lxj.community.model.User;
import life.lxj.community.provide.Githubprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by asus on 2019/10/8.
 */


@Controller
public class AuthorizeController {
    @Autowired
    private Githubprovider githubprovider;

    @Value("${github.cline.id}")
    private String clineId;
    @Value("${github.cline.secret}")
    private String clineSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse respones){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clineId);
        accessTokenDTO.setClient_secret(clineSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubprovider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubprovider.getUser(accessToken);
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            respones.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user" , githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
