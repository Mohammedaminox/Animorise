package com.animo.animorise.repository;

import com.animo.animorise.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByOwnerId(Long ownerId);
}
