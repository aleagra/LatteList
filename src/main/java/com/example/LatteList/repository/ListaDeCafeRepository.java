package com.example.LatteList.repository;

import com.example.LatteList.model.ListaDeCafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaDeCafeRepository extends JpaRepository<ListaDeCafe, Long> {

}
