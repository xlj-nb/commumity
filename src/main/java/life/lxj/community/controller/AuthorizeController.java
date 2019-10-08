package life.lxj.community.controller;

import life.lxj.community.dto.AccessTokenDTO;
import life.lxj.community.dto.GithubUser;
import life.lxj.community.provide.Githubprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private String clinrSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,@RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clineId);
        accessTokenDTO.setClient_secret(clinrSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubprovider.getAccessToken(accessTokenDTO);
        GithubUser user = githubprovider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
