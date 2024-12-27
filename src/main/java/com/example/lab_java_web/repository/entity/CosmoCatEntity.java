package com.example.lab_java_web.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "cosmo_cats")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CosmoCatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cosmo_cat_id_seq")
    @SequenceGenerator(name = "cosmo_cat_id_seq", sequenceName = "cosmo_cat_id_seq")
    private Long id;
    @NaturalId
    @Column(unique = true, nullable = false)
    private UUID cosmoCatId;
    @Column(name = "cat_cosmoname", nullable = false)
    private String catCosmoName;
    @Column(name = "real_name", nullable = false)
    private String realName;
    @Column(name = "cat_email", nullable = false, unique = true)
    private String catEmail;
    @OneToMany(mappedBy = "cosmoCat", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
