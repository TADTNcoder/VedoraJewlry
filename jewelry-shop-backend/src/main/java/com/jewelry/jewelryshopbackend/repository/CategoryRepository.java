package com.jewelry.jewelryshopbackend.repository;

import com.jewelry.jewelryshopbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsBySlugAndIdNot(String slug, Long id);

    Optional<Category> findByIdAndStatusTrue(Long id);

    List<Category> findAllByStatusTrue();
}