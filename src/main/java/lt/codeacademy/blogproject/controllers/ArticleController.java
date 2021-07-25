package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.controllers.dto.ArticleRequest;
import lt.codeacademy.blogproject.controllers.dto.ArticleResponse;
import lt.codeacademy.blogproject.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/articles/create")
    public String createArticle (@RequestParam ArticleRequest articleRequest) throws IOException {

        articleService.createArticle(articleRequest);
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/articleDelete")
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

    @GetMapping(value = "/articleGetOne")
    public ArticleResponse getOneArticle(@RequestParam(value = "id") Long id){
        return articleService.getOneArticle(id);
    }

    @GetMapping(value = "/getListByDrinkType")
    public List<ArticleResponse> getAllArticlesByDrinkType(@RequestBody String drinkType) {
        return articleService.getArticlesByDrinkType(drinkType);
    }
}
