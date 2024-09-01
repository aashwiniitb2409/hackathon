package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> search(String name);
    List<Asset> findByTypeContainingIgnoreCase(String type);

    @Query(value = "SELECT * FROM assets ORDER BY embedding <-> :queryEmbedding LIMIT 10", nativeQuery = true)
    List<Asset> findAssetsByEmbedding(List<Double> queryEmbedding);
}