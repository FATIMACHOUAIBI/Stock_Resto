package com.example.stock_resto.Repositories;

import com.example.stock_resto.entities.Categorie;
import com.example.stock_resto.entities.ProduitRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public interface ProduitRefRepository extends JpaRepository<ProduitRef, Integer> {
    List<ProduitRef> findProduitRefByNomProduitRefContainingIgnoreCase(String keyword);
}
