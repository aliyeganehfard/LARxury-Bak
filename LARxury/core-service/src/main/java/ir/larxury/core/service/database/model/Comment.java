package ir.larxury.core.service.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "answer_date")
    private Date answerDate;

    @Column(name = "text" , nullable = false)
    private String text;

    @Column(name = "answer")
    private String answer;

    @Column(name = "commenter_user_id", nullable = false)
    private String commenterUserId;

    @Column(name = "commenter_user_name")
    private String commenterUsername;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Product product;

    @PrePersist
    public void prePersist() {
        this.createDate = new Date();
    }
}
