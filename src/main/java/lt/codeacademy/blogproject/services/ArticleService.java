package lt.codeacademy.blogproject.services;

import lt.codeacademy.blogproject.controllers.dto.ArticleRequest;
import lt.codeacademy.blogproject.controllers.dto.ArticleResponse;
import lt.codeacademy.blogproject.repositories.ArticleRepository;
import lt.codeacademy.blogproject.repositories.BlogUserRepository;
import lt.codeacademy.blogproject.repositories.DrinkCategoryRepository;
import lt.codeacademy.blogproject.repositories.dao.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleDao;
    private final BlogUserRepository blogUserDao;
    private final DrinkCategoryRepository drinkCategoryDao;

    @Autowired
    public ArticleService(ArticleRepository articleDao, BlogUserRepository blogUserDao, DrinkCategoryRepository drinkCategoryDao) {
        this.articleDao = articleDao;
        this.blogUserDao = blogUserDao;
        this.drinkCategoryDao = drinkCategoryDao;
    }

    @Transactional
    public ArticleResponse createArticle(ArticleRequest articleRequest) {
        ArticleResponse articleResponse = new ArticleResponse();

        articleDao.save(new Article
                (articleRequest.getTitle(),
                articleRequest.getText(),
                articleRequest.getImage(),
                blogUserDao.getBlogUserByUsername(articleRequest.getCreator()),
                drinkCategoryDao.getDrinkCategoryByCategoryName(articleRequest.getDrinkCategory())));

        articleResponse.setTitle(articleRequest.getTitle());
        articleResponse.setImage(articleRequest.getImage());
        articleResponse.setText(articleRequest.getText());
        articleResponse.setCreatedAt(LocalDateTime.now());
        articleResponse.setCreator(articleRequest.getCreator());
        articleResponse.setDrinkCategory(articleRequest.getDrinkCategory());
        return articleResponse;
    }

    @Transactional
    public void deleteArticle(ArticleRequest articleRequest){
        articleDao.delete(articleDao.getArticleById(articleRequest.getId()));
    }

    @Transactional
    public ArticleResponse updateArticle(ArticleRequest articleRequest) {
        ArticleResponse articleResponse = new ArticleResponse();
        Article articleToUpdate = articleDao.getArticleById(articleRequest.getId());

        articleToUpdate.setTitle(articleRequest.getTitle());
        articleToUpdate.setText(articleRequest.getText());
        articleToUpdate.setCreator(blogUserDao.getBlogUserByUsername(articleRequest.getCreator()));
        articleToUpdate.setImage(articleRequest.getImage());
        articleToUpdate.setDrinkCategory(drinkCategoryDao.getDrinkCategoryByCategoryName(articleRequest.getDrinkCategory()));

        articleDao.save(articleToUpdate);

        articleResponse.setTitle(articleRequest.getTitle());
        articleResponse.setImage(articleRequest.getImage());
        articleResponse.setText(articleRequest.getText());
        articleResponse.setCreatedAt(LocalDateTime.now());
        articleResponse.setCreator(articleRequest.getCreator());
        articleResponse.setDrinkCategory(articleRequest.getDrinkCategory());

        return articleResponse;
    }

    public List<ArticleResponse> getAllArticles(){
        return articleDao.getArticles()
                .stream()
                .map(article -> new ArticleResponse(
                        article.getId(),
                        article.getCreatedAt(),
                        article.getTitle(),
                        article.getText(),
                        article.getCreator().getUsername(),
                        article.getImage(),
                        article.getDrinkCategory().getCategoryName()))
                .collect(Collectors.toList());
    }

    public ArticleResponse getOneArticle(Long id){
        ArticleResponse articleResponse = new ArticleResponse();
        Article article = articleDao.getArticleById(id);

        articleResponse.setId(id);
        articleResponse.setCreator(article.getCreator().getUsername());
        articleResponse.setCreatedAt(article.getCreatedAt());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setText(article.getText());
        articleResponse.setImage(article.getImage());
        articleResponse.setDrinkCategory(article.getDrinkCategory().getCategoryName());
        return articleResponse;
    }

    public List<ArticleResponse> getArticlesByDrinkType(String drinkType){
        return articleDao.getArticleByDrinkCategory(drinkType)
                .stream()
                .map(article -> new ArticleResponse(
                        article.getId(),
                        article.getCreatedAt(),
                        article.getTitle(),
                        article.getText(),
                        article.getCreator().getUsername(),
                        article.getImage(),
                        article.getDrinkCategory().getCategoryName()))
                .collect(Collectors.toList());
    }
}
