package lt.codeacademy.blogproject.services;

import lt.codeacademy.blogproject.controllers.dto.ArticleRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogCommentRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogCommentResponse;
import lt.codeacademy.blogproject.repositories.ArticleRepository;
import lt.codeacademy.blogproject.repositories.BlogCommentRepository;
import lt.codeacademy.blogproject.repositories.BlogUserRepository;
import lt.codeacademy.blogproject.repositories.dao.BlogComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogCommentService {

    private final BlogCommentRepository blogCommentDao;
    private final BlogUserRepository blogUserDao;
    private final ArticleRepository articleDao;

    @Autowired
    public BlogCommentService(BlogCommentRepository blogCommentDao, BlogUserRepository blogUserDao, ArticleRepository articleDao) {
        this.blogCommentDao = blogCommentDao;
        this.blogUserDao = blogUserDao;
        this.articleDao = articleDao;
    }

    @Transactional
    public BlogCommentResponse createBlogComment(BlogCommentRequest blogCommentRequest){
        BlogCommentResponse blogCommentResponse = new BlogCommentResponse();

        blogCommentDao.save(new BlogComment
                (blogCommentRequest.getText(),
                 blogUserDao.getBlogUserByUsername(blogCommentRequest.getCreator()),
                 articleDao.getArticleById(blogCommentRequest.getArticle_id())));

        blogCommentResponse.setText(blogCommentRequest.getText());
        blogCommentResponse.setCreator(blogCommentRequest.getCreator());
        blogCommentResponse.setCreatedAt(LocalDateTime.now());
        blogCommentResponse.setArticle_id(blogCommentRequest.getArticle_id());
        return blogCommentResponse;
    }

    @Transactional
    public BlogCommentResponse updateBlogComment(BlogCommentRequest blogCommentRequest){
        BlogCommentResponse blogCommentResponse = new BlogCommentResponse();
        BlogComment blogCommentToUpdate = blogCommentDao.getBlogCommentById(blogCommentRequest.getId());

        blogCommentToUpdate.setText(blogCommentRequest.getText());

        blogCommentDao.save(blogCommentToUpdate);

        blogCommentResponse.setId(blogCommentRequest.getId());
        blogCommentResponse.setCreator(blogCommentRequest.getCreator());
        blogCommentResponse.setText(blogCommentToUpdate.getText());
        blogCommentResponse.setArticle_id(blogCommentRequest.getArticle_id());
        return blogCommentResponse;
    }

    @Transactional
    public void deleteBlogComment(BlogCommentRequest blogCommentRequest){
        blogCommentDao.delete(blogCommentDao.getBlogCommentById(blogCommentRequest.getId()));
    }

    public List<BlogCommentResponse> getAllBlogCommentsForArticle(Long article_id){
//        return blogCommentDao
//                .getBlogCommentsByArticle(articleDao.getArticleById(article_id))
//                .stream()
//                .map(comment -> new BlogCommentResponse(
//                        comment.getId(),
//                        comment.getText(),
//                        comment.getCreatedAt(),
//                        comment.getCreator().getUsername(),
//                        comment.getArticle().getId()))
//                .collect(Collectors.toList());

        return blogCommentDao
                .getBlogCommentsForArticle(article_id)
                .stream()
                .map(comment -> new BlogCommentResponse(
                        comment.getId(),
                        comment.getText(),
                        comment.getCreatedAt(),
                        comment.getCreator().getUsername(),
                        comment.getArticle().getId()))
                .collect(Collectors.toList());
    }
}
