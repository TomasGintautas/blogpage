package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.controllers.dto.ArticleRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogCommentRequest;
import lt.codeacademy.blogproject.services.ArticleService;
import lt.codeacademy.blogproject.services.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BlogCommentService commentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/articles/create")
    public String createArticle (@Valid ArticleRequest articleRequest) throws IOException {
        articleRequest.setCreator(SecurityContextHolder.getContext().getAuthentication().getName());
        articleService.createArticle(articleRequest);
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/articles/create")
    public String createArticle(Model model){
        model.addAttribute("articleRequest", new ArticleRequest());
        return "/articles/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/articles/{id}/delete")
    public String deleteArticle (@PathVariable("id") Long id){
        articleService.deleteArticle(id);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/articles/{id}/edit")
    public String updateArticle(@Valid ArticleRequest articleRequest) throws IOException {
        articleService.updateArticle(articleRequest);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/articles/{id}/edit")
    public String updateArticle(@PathVariable("id") Long id, Model model) throws IOException {
        model.addAttribute("articleEdit", articleService.getOneArticle(id));
        return "/articles/edit";
    }

    @GetMapping(value = "/index")
    public String getAllArticles(Model model){
        model.addAttribute("articleList", articleService.getAllArticles());
        return "index";
    }

    @GetMapping(value = "/articles/{id}/view")
    public String getOneArticle(@PathVariable("id") Long id, Model model){
        model.addAttribute("article", articleService.getOneArticle(id));
        model.addAttribute("commentList", commentService.getAllBlogCommentsForArticle(id));
        model.addAttribute("newCommentRequest", new BlogCommentRequest());
        return "articles/view";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/articles/{id}/view")
    public String createComment(@Valid BlogCommentRequest blogCommentRequest, @PathVariable("id") Long id){
        blogCommentRequest.setArticle_id(id);
        blogCommentRequest.setCreator(SecurityContextHolder.getContext().getAuthentication().getName());
        commentService.createBlogComment(blogCommentRequest);
        return "redirect:/";
    }

    @GetMapping
    public String home(HttpServletRequest request, HttpSession session) {
        return "redirect:/index";
    }
}
