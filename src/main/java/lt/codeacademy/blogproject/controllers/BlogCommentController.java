package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.controllers.dto.BlogCommentRequest;
import lt.codeacademy.blogproject.services.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;

    @PreAuthorize("hasRole('USER') || principal.username == #comments.creator")
    @PostMapping(value = "/comment/{id}/edit")
    public String updateBlogComment(@Valid BlogCommentRequest blogCommentRequest){
        blogCommentService.updateBlogComment(blogCommentRequest);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('USER') || principal.username == #comments.creator")
    @GetMapping(value = "/comment/{id}/edit")
    public String updateBlogComment(@PathVariable("id") Long id, Model model){
        model.addAttribute("comment", blogCommentService.getOneComment(id));
        return "/comment/edit";
    }

    @PreAuthorize("hasRole('ADMIN') || principal.username == #comments.creator")
    @GetMapping(value = "/comment/{id}/delete")
    public String deleteBlogComment(@PathVariable("id") Long id){
        blogCommentService.deleteBlogComment(id);
        return "redirect:/";
    }
}
