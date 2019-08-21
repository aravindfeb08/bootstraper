package com.home.search.Controller;

import com.home.search.Config.JwtTokenUtil;
import com.home.search.Form.UserForm;
import com.home.search.Model.Roles;
import com.home.search.Model.User;
import com.home.search.Service.BasicUserDetails;
import com.home.search.Service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final Log LOG = LogFactory.getLog(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BasicUserDetails userDetailsService;

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestParam("userName") String userName, @RequestParam("password") String password,
                                        HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        JSONObject failurJson = new JSONObject();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isValid = false;
        String host = request.getHeader("Server");
        try {
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                json.put("success", "found user");
                isValid = passwordEncoder.matches(password, user.getPassword());
                if (isValid) {
                   /* Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                    UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(user.getUsername(), password, authorities);
                    System.out.println(token1);*/
                    final Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getUserName(), password));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    final String token = jwtTokenUtil.generateToken(authentication, user.getUserId(), user.getUsername());
                    json.put("token", token);
                    json.put("role", user.getRoles());
                    json.put("success", "valid username and password");
                    response.addHeader("name",user.getUsername());
                    response.addHeader("headerToken", token);
                    response.addHeader("role",user.getRoles().toString());
                } else {
                    json.put("failure", "invalid password");
                }
            } else {
                json.put("failure", "invalid username");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public String logout() {
        return "Hello World";
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@ModelAttribute("userForm") UserForm userForm, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        boolean isCreated = false;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            User user = new User();
            user.setUserId(userForm.getUserId());
            user.setFirstName(userForm.getFirstName());
            user.setUserName(userForm.getUserName());
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
            Roles role = userService.findByRole(userForm.getRole());
            user.setRoles(new HashSet<>(Arrays.asList(role)));
            isCreated = userService.saveUser(user);
            user = userService.getUserByUserName(userForm.getUserName());
            if (user != null) {
                final Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), userForm.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                final String token = jwtTokenUtil.generateToken(authentication, user.getUserId(), user.getUsername());
                json.put("success", "user created successfully");
                json.put("token", token);
                response.addHeader("token", token);
                response.addHeader("headerToken", token);
                response.addHeader("role",user.getRoles().toString());
            } else {
                json.put("failure", "user not created");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in signup method : " + exp);
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PostMapping("/setRoles")
    public ResponseEntity<String> setRoles(@ModelAttribute("userForm") UserForm userForm, HttpServletResponse response){
        JSONObject json = new JSONObject();
        boolean isCreated = false;
        try{
            Roles role = new Roles();
            role.setRoleId(userForm.getRoleId());
            role.setRole(userForm.getRole());
            isCreated = userService.saveRoles(role);
            if (isCreated){
                json.put("success", "role created successfully");
            }else {
                json.put("failure", "role not created");
            }
        }catch (Exception exp){
            exp.printStackTrace();
            LOG.error("Exception in setRoles method : "+exp);
        }
        return new ResponseEntity<>(json.toString(),HttpStatus.OK);
    }

}

