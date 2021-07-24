package lt.codeacademy.blogproject.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {

    private Long   id;
    private String title;
    private String text;
    private String creator;
    private MultipartFile image;
    private String drinkCategory;
}
