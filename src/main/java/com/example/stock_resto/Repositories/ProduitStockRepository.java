package com.example.stock_resto.Repositories;
import com.example.stock_resto.entities.ProduitStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProduitStockRepository extends JpaRepository<ProduitStock, Integer> {
    List<ProduitStock>findByProduitRefNomProduitRefLikeIgnoreCase(String keyword);
}




