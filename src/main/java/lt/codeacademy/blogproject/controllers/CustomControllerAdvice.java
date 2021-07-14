package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.repositories.dao.BlogUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CustomControllerAdvice {

    // krauna useri
    @ModelAttribute("user")
    public BlogUser user(){
        return new BlogUser();
    }
}
