package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.controllers.dto.ArticleRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogCommentRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogCommentResponse;
import lt.codeacademy.blogproject.services.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogCommentController {

    @Autowired
    private BlogCommentService blogCommentService;

    @PostMapping(value = "/commentCreate")
    public ResponseEntity createBlogComment (@RequestBody BlogCommentRequest blogCommentRequest){
        try{
            return ResponseEntity.ok(blogCommentService.createBlogComment(blogCommentRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/commentUpdate")
    public ResponseEntity updateBlogComment(@RequestBody BlogCommentRequest blogCommentRequest){
        try{
            return ResponseEntity.ok(blogCommentService.updateBlogComment(blogCommentRequest));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/commentDelete")
    public void deleteBlogComment(@RequestBody BlogCommentRequest blogCommentRequest){
        blogCommentService.deleteBlogComment(blogCommentRequest);
    }

    @GetMapping(value = "/getCommentsForArticle")
    @ResponseBody
    public List<BlogCommentResponse> getAllBlogCommentsForArticle(@RequestParam(value = "article_id") Long article_id){
        return blogCommentService.getAllBlogCommentsForArticle(article_id);
    }
}
