package com.example.stock_resto.Repositories;
import com.example.stock_resto.entities.Statue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StatueRepository extends JpaRepository<Statue, Integer> {

    List<Statue> findByNomStatueContainingIgnoreCase(String keyword);}