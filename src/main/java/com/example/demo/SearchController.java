package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private AssetRepository assetRepository; // Hypothetical repository for querying assets

    @PostMapping("/searchAssets")
    public ResponseEntity<List<Asset>> searchAssets(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        if (query == null || query.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Generate embeddings for the query
        List<List<Double>> queryEmbedding = embeddingService.getEmbeddings(List.of(query));

        // Query PostgreSQL with pgvector
        List<Asset> assets = assetRepository.findAssetsByEmbedding(queryEmbedding.get(0));

        return ResponseEntity.ok(assets);
    }
}