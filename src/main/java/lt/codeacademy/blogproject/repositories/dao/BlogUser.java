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
@Table(name = "blog_user")
public class BlogUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "access")
    private AccessRights access;

    @Transient
    @OneToOne(mappedBy = "creator")
    @JoinColumn(name = "article_id")
    private Article article;

    @Transient
    @OneToOne(mappedBy = "creator")
    @JoinColumn(name = "comment_id")
    private BlogComment blogComment;

    public BlogUser(String username, String password, AccessRights access) {
        this.username = username;
        this.password = password;
        this.access = access;
    }
}
