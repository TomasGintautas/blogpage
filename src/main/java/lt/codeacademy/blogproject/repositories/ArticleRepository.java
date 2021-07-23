package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.repositories.dao.Article;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

    default List<Article> getArticles(){
        return this.findAll();
    }

    Article getArticleById(Long id);

    Article getArticleByTitle(String title);

    List<Article> getArticleByDrinkCategory(String category);
}
