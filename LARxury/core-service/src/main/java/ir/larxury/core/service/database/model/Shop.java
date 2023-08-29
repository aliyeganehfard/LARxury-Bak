package ir.larxury.core.service.database.model;

import ir.larxury.core.service.database.model.enums.ShopStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "about")
    private String about;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @JoinTable(
            name = "shop_category",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @JoinColumn(name = "place_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ShopPlace place;


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
