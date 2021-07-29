package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.controllers.dto.ArticleRequest;
import lt.codeacademy.blogproject.controllers.dto.ArticleResponse;
import lt.codeacademy.blogproject.controllers.dto.BlogCommentRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogUserRequest;
import lt.codeacademy.blogproject.repositories.dao.Article;
import lt.codeacademy.blogproject.services.ArticleService;
import lt.codeacademy.blogproject.services.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BlogCommentService commentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/articles/create")
    public String createArticle (@Valid ArticleRequest articleRequest) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        articleRequest.setCreator(currentPrincipalName);
        articleService.createArticle(articleRequest);
        return "index";
    }

    @GetMapping(value = "/articles/create")
    public String createArticle(Model model){
        model.addAttribute("articleRequest", new ArticleRequest());
        return "/articles/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/deleteArticle")
    public void deleteArticle (@RequestBody ArticleRequest articleRequest){
        articleService.deleteArticle(articleRequest);
    }

//    @PostMapping(value = "/articleUpdate")
//    public ResponseEntity updateArticle(@RequestBody ArticleRequest articleRequest){
//        try{
//            return ResponseEntity.ok(articleService.updateArticle(articleRequest));
//        }
//        catch (Exception e){
//            return ResponseEntity.badRequest().build();
//        }
//    }

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

    @PostMapping(value = "/articles/{id}/view")
    public String createComment(@Valid BlogCommentRequest blogCommentRequest, @PathVariable("id") Long id){
        blogCommentRequest.setArticle_id(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        blogCommentRequest.setCreator(authentication.getName());
        commentService.createBlogComment(blogCommentRequest);
        return "redirect:/";
    }

    @GetMapping(value = "/getListByDrinkType")
    public List<ArticleResponse> getAllArticlesByDrinkType(@RequestBody String drinkType) {
        return articleService.getArticlesByDrinkType(drinkType);
    }

    @GetMapping
    public String home(HttpServletRequest request, HttpSession session) {
        return "redirect:/index";
    }
}
