package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.controllers.dto.ArticleRequest;
import lt.codeacademy.blogproject.controllers.dto.ArticleResponse;
import lt.codeacademy.blogproject.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/articleCreate")
    public ResponseEntity createArticle (@RequestBody ArticleRequest articleRequest){
        try{
            return ResponseEntity.ok(articleService.createArticle(articleRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/articleDelete")
    public void deleteArticle (@RequestBody ArticleRequest articleRequest){
        articleService.deleteArticle(articleRequest);
    }

    @PostMapping(value = "/articleUpdate")
    public ResponseEntity updateArticle(@RequestBody ArticleRequest articleRequest){
        try{
            return ResponseEntity.ok(articleService.updateArticle(articleRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/articleGetAll")
    public List<ArticleResponse> getAllArticles(){
        return articleService.getAllArticles();
    }

    @GetMapping(value = "/articleGetOne")
    public ArticleResponse getOneArticle(@RequestParam(value = "id") Long id){
        return articleService.getOneArticle(id);
    }
}
