package io.github.renatompf.springelasticpoc.repository.jpa;

import io.github.renatompf.springelasticpoc.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {}