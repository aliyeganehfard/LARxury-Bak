package ir.larxury.core.service.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "follower")
public class Follower {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @ToString.Exclude
    @ManyToMany(mappedBy = "followers",fetch = FetchType.LAZY)
    private List<Shop> followers = new ArrayList<>();

}
