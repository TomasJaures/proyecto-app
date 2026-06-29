package com.group.rua.entities;

import jakarta.persistence.*;

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
    public Boolean isAnulled = false;
}