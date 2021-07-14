package lt.codeacademy.blogproject.repositories.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog_comment")
public class BlogComment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "blog_user_id")
    private BlogUser creator;

    @ManyToOne
    @JoinColumn(name="article_id", nullable = false)
    private Article article;

    public BlogComment(String text, BlogUser creator, Article article) {
        this.text = text;
        this.creator = creator;
        this.article = article;
    }
}
