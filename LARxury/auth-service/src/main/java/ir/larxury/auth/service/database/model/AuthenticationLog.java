package ir.larxury.auth.service.database.model;

import ir.larxury.auth.service.database.model.enums.AuthenticationOperationType;
import ir.larxury.auth.service.database.model.enums.AuthenticationStatus;
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
@Table(name = "authentication_log")
public class AuthenticationLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthenticationStatus status;

    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private AuthenticationOperationType operationType;

    @Column(name = "date", nullable = false)
    private Date date;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @PrePersist
    public void prePersist(){
        this.date = new Date();
    }
}
