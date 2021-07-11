package lt.codeacademy.blogproject.repositories.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private String image;

    @OneToOne(mappedBy = "article")
    @JoinColumn(name = "blog_user_id")
    private BlogUser creator;

    @OneToMany(mappedBy = "article",
            cascade = CascadeType.ALL)
    private List<BlogComment> blogComments;

    public Article(String title, String text, String image, BlogUser creator) {
        this.title = title;
        this.text = text;
        this.image = image;
        this.creator = creator;
    }
}
