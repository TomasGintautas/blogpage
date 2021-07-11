package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.controllers.dto.BlogUserRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogUserResponse;
import lt.codeacademy.blogproject.services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogUserController {

    @Autowired
    private BlogUserService blogUserService;

    @PostMapping(value = "/createUser")
    public ResponseEntity createBlogUser(@RequestBody BlogUserRequest blogUserRequest){
        try{
            return ResponseEntity.ok(blogUserService.createBlogUser(blogUserRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/getUser")
    public String getBlogUserUsername(Long id){
        return blogUserService.getBlogUserUsername(id).getUsername();
    }

    @GetMapping(value = "/allBlogUsers")
    public List<BlogUserResponse> getBlogUsers(){
        return blogUserService.getBlogUsers();
    }
}
