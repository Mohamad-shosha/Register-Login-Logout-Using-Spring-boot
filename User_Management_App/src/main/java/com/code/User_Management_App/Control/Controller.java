package com.code.User_Management_App.Control;
import com.code.User_Management_App.Service.UserService;
import com.code.User_Management_App.model.UserDtls;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class Controller {
    private UserService userService;
    @Autowired
    public Controller(UserService service){
        this.userService = service;
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "fancy-login";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model themodel , UserDtls dtls) {
        UserDtls userDtls = new UserDtls();
        themodel.addAttribute("user", dtls);
        return "register";
    }
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") UserDtls dtls, HttpSession session) {
        // Validate form data
        if (dtls.getEmail() == null || dtls.getEmail().isEmpty()) {
            session.setAttribute("msg", "Email cannot be empty");
            return "redirect:/register";
        }

        // Check if email already exists
        boolean emailExists = userService.checkEmail(dtls.getEmail());
        if (emailExists) {
            session.setAttribute("msg", "Email already exists");
            return "redirect:/register";
        }

        // Save user if everything is valid
        UserDtls createdUser = userService.creatUser(dtls);
        if (createdUser != null) {
            session.setAttribute("msg", "Registered Successfully");
        } else {
            session.setAttribute("msg", "Something went wrong on server");
        }

        return "redirect:/register";
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                ).logout( logout -> logout.permitAll() );
        return http.build();
    }

}