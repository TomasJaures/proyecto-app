package com.group.rua.Entities_Classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    public Integer classId;

    @Column(name = "block_id")
    public Integer blockId;

    @Column(name = "is_anulled")
    public Boolean isAnulled;
}