package lt.codeacademy.blogproject.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogCommentRequest {

    private Long id;
    private String text;
    private String creator;
    private Long article_id;
}
