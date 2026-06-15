package dev.sorokin.eventmanager.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "location")
    private List<EventEntity> events = new ArrayList<>();

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