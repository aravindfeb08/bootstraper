package com.home.search.Controller;

import com.home.search.Config.JwtTokenUtil;
import com.home.search.Form.UserForm;
import com.home.search.Model.Role;
import com.home.search.Model.User;
import com.home.search.Service.BasicUserDetails;
import com.home.search.Service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
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

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password,
                                   HttpRequest request, HttpResponse response) {
        JSONObject json = new JSONObject();
        JSONObject failurJson = new JSONObject();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isValid = false;
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.getUserByUserName(authentication.getName());
            if (user != null) {
                json.put("success", "found user");
                isValid = passwordEncoder.matches(password, user.getPassword());
                System.out.println(isValid);
                if (isValid) {
                    final String token = jwtTokenUtil.generateToken(authentication, user.getUserId(), user.getUsername());
                    response.addHeader("headerToken", token);
                    json.put("token", token);
                    json.put("role", user.getRoles());
                    json.put("success", "valid username and password");
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
            Role role = userService.findByRole("ROLE_USER");
            user.setRoles(new HashSet<>(Arrays.asList(role)));
            isCreated = userService.saveUser(user);
            if (isCreated) {
                final Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
                final String token = jwtTokenUtil.generateToken(authentication, user.getUserId(), user.getUsername());
                json.put("success", "user created successfully");
                json.put("token", token);
                response.addHeader("token", token);
            } else {
                json.put("failure", "user not created");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

}

