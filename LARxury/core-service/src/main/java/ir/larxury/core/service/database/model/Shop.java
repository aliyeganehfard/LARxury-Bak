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
@Table(name = "shop")
public class Shop {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Long userId;


    @PrePersist
    public void prePersist(){
        var date = new Date();
        this.createDate = date;
        this.updateDate = date;
    }

    @PreUpdate
    public void preUpdate(){
        this.updateDate = new Date();
    }
}
