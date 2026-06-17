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
@Table(name = "qr_token")
public class QrToken {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qr_id")
    public Integer qrId;

    @Column(name = "content", columnDefinition = "TEXT")
    public String content;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "expiration_at")
    public LocalDateTime expirationAt;

    // Relación 1:1 con Class (UNIQUE en BD). ON DELETE CASCADE está definido en la BD.
    @OneToOne
    @JoinColumn(name = "class_id")
    public Classes classEntity;

}
