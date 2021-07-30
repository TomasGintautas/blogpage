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
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleDao;
    private final BlogUserRepository blogUserDao;
    private final DrinkCategoryRepository drinkCategoryDao;
    private final FileStorageService fileStorageService;

    @Autowired
    public ArticleService(ArticleRepository articleDao, BlogUserRepository blogUserDao, DrinkCategoryRepository drinkCategoryDao, FileStorageService fileStorageService) {
        this.articleDao = articleDao;
        this.blogUserDao = blogUserDao;
        this.drinkCategoryDao = drinkCategoryDao;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public Article createArticle(ArticleRequest articleRequest) throws IOException {
        return articleDao.save(new Article
                (articleRequest.getTitle(),
                articleRequest.getText(),
                fileStorageService.save(articleRequest.getImage()),
                blogUserDao.getBlogUserByUsername(articleRequest.getCreator()),
                drinkCategoryDao.getDrinkCategoryByCategoryName(articleRequest.getDrinkCategory())));
    }

    @Transactional
    public void deleteArticle(Long id){
        articleDao.delete(articleDao.getArticleById(id));
    }

    @Transactional
    public void updateArticle(ArticleRequest articleRequest) throws IOException {
        Article articleToUpdate = articleDao.getArticleById(articleRequest.getId());

        if(articleRequest.getTitle() != null) {
            articleToUpdate.setTitle(articleRequest.getTitle());
        }
        if(articleRequest.getText() != null) {
            articleToUpdate.setText(articleRequest.getText());
        }
        if(articleRequest.getImage() != null) {
            articleToUpdate.setImage(fileStorageService.save(articleRequest.getImage()));
        }
        if(articleRequest.getDrinkCategory() != null) {
            articleToUpdate.setDrinkCategory(drinkCategoryDao.getDrinkCategoryByCategoryName(articleRequest.getDrinkCategory().toUpperCase()));
        }
        articleDao.save(articleToUpdate);
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
