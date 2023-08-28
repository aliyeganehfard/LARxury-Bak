package ir.larxury.core.service.database.model;

import ir.larxury.core.service.database.model.enums.PlaceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "shop_place")
public class ShopPlace {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PlaceStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopPlace shopPlace = (ShopPlace) o;
        return Objects.equals(id, shopPlace.id) && Objects.equals(name, shopPlace.name) && Objects.equals(address, shopPlace.address) && status == shopPlace.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, status);
    }
}
