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
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private String image;

    @OneToOne
    @JoinColumn(name = "blog_user_id")
    private BlogUser creator;

    @OneToOne
    @JoinColumn(name = "drink_category_id")
    private DrinkCategory drinkCategory;

    @OneToMany(mappedBy = "article",
            cascade = CascadeType.ALL)
    private List<BlogComment> blogComments;

    public Article(String title, String text, String image, BlogUser creator, DrinkCategory drinkCategory) {
        this.title = title;
        this.text = text;
        this.image = image;
        this.creator = creator;
        this.drinkCategory = drinkCategory;
    }
}
