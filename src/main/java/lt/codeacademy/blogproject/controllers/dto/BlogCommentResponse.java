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
public class BlogCommentResponse {

    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private String creator;
    private Long article_id;
}
