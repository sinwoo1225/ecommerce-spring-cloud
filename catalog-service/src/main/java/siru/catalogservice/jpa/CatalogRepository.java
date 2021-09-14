package siru.catalogservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import siru.catalogservice.entity.CatalogEntity;

@Repository
public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String productId);
}