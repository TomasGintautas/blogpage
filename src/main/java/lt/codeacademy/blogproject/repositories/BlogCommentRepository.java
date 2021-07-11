package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.repositories.dao.Article;
import lt.codeacademy.blogproject.repositories.dao.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    default List<BlogComment> getBlogComments(){
        return this.findAll();
    }

    BlogComment getBlogCommentById(Long id);

    List<BlogComment> getBlogCommentsByArticle(Article article);
}
