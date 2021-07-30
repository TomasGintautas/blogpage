package lt.codeacademy.blogproject.services;

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
    public void createBlogComment(BlogCommentRequest blogCommentRequest) {
        blogCommentDao.save(new BlogComment
                (blogCommentRequest.getText(),
                        blogUserDao.getBlogUserByUsername(blogCommentRequest.getCreator()),
                        articleDao.getArticleById(blogCommentRequest.getArticle_id())));
    }

    @Transactional
    public void updateBlogComment(BlogCommentRequest blogCommentRequest) {
        BlogComment blogCommentToUpdate = blogCommentDao.getBlogCommentById(blogCommentRequest.getId());

        blogCommentToUpdate.setText(blogCommentRequest.getText());
        blogCommentDao.save(blogCommentToUpdate);
    }

    @Transactional
    public void deleteBlogComment(Long id) {
        blogCommentDao.delete(blogCommentDao.getBlogCommentById(id));
    }

    public List<BlogCommentResponse> getAllBlogCommentsForArticle(Long article_id) {
        return blogCommentDao
                .getBlogCommentsByArticle(articleDao.getArticleById(article_id))
                .stream()
                .map(comment -> new BlogCommentResponse(
                        comment.getId(),
                        comment.getText(),
                        comment.getCreatedAt(),
                        comment.getCreator().getUsername(),
                        comment.getArticle().getId()))
                .collect(Collectors.toList());
    }

    public BlogComment getOneComment(Long id) {
        return  blogCommentDao.getBlogCommentById(id);
    }
}
