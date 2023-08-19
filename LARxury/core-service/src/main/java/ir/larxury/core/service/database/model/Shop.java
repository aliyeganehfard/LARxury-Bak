package ir.larxury.core.service.database.model;

import ir.larxury.core.service.database.model.enums.ShopStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "shop_staus", nullable = false)
    private ShopStatus shopStatus;

    @Column(name = "user_id", nullable = false)
    private String userId;


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
