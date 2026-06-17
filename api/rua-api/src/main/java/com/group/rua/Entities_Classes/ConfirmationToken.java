package com.group.rua.Entities_Classes;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    public Integer tokenId;

    @Column(name = "content", nullable = false)
    public String content;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "expire_at", nullable = false)
    public LocalDateTime expireAt;

    // Relación 1:1 con UnconfirmedUser (UNIQUE en BD)
    @OneToOne
    @JoinColumn(name = "user_id")
    public UnconfirmedUser unconfirmedUser;

}

