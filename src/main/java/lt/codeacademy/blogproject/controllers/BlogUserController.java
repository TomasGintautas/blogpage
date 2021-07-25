package lt.codeacademy.blogproject.controllers;

import lombok.SneakyThrows;
import lt.codeacademy.blogproject.controllers.dto.BlogUserRequest;
import lt.codeacademy.blogproject.repositories.dao.BlogUser;
import lt.codeacademy.blogproject.services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BlogUserController {

    @Autowired
    private BlogUserService blogUserService;

    @SneakyThrows
    @PostMapping(value = "/register")
    public String createBlogUser(@Valid BlogUserRequest blogUserRequest, Model model){

        if(!blogUserRequest.getPassword().equals(blogUserRequest.getConfirmPassword())){
            model.addAttribute("blogUserRequest", blogUserRequest);
            return "register";
        }
        else{
            blogUserService.createBlogUser(blogUserRequest);
        }
        return "redirect:/";
    }

    @GetMapping(value = "/register")
    public String createBlogUser(Model model){
        model.addAttribute("blogUserRequest", new BlogUserRequest());
        return "register";
    }

    @GetMapping
    public String getLogin(Model model, @AuthenticationPrincipal BlogUser blogUser){
        if(blogUser == null){
            model.addAttribute("blogUser", new BlogUser());
            return "index";
        }
        return "redirect:/";
    }
}