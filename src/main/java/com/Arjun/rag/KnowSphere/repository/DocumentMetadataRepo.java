package com.Arjun.rag.KnowSphere.repository;

import com.Arjun.rag.KnowSphere.entity.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentMetadataRepo extends JpaRepository<DocumentMetadata, UUID> {
}
