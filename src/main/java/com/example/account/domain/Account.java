package com.example.account.domain;

import com.example.account.exception.AccountException;
import com.example.account.type.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.example.account.type.ErrorCode.AMOUNT_EXCEED_BALANCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private AccountUser accountUser;
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    public void useBalance(Long amount) {
        if (amount > balance) {
            throw new AccountException(AMOUNT_EXCEED_BALANCE);
        }
        balance -= amount;
    }
}