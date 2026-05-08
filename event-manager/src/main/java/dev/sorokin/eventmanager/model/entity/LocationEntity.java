package dev.sorokin.eventmanager.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "locations")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String description;

    public LocationEntity(
            String name,
            String address,
            Integer capacity,
            String description
    ) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }

}