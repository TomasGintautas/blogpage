package lt.codeacademy.blogproject.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {

    private Long id;
    private LocalDateTime createdAt;
    private String title;
    private String text;
    private String creator;
    private String image;
    private String drinkCategory;
}
