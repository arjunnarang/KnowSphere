package com.Arjun.rag.KnowSphere.service.impl;

import com.Arjun.rag.KnowSphere.config.AppProperties;
import com.Arjun.rag.KnowSphere.entity.DocumentMetadata;
import com.Arjun.rag.KnowSphere.entity.DocumentStatus;
import com.Arjun.rag.KnowSphere.exception.DocumentProcessingException;
import com.Arjun.rag.KnowSphere.repository.DocumentMetadataRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentIngestService {

    private static final Logger log = LoggerFactory.getLogger(DocumentIngestService.class);

    private final VectorStore vectorStore;
    private final DocumentMetadataRepo documentMetadataRepo;
    private final AppProperties appProperties;


    public int ingest(DocumentMetadata metadata, List<Document> parsedDocs) {

        log.info("Ingesting document: {}, document name: {}, document size: {}", metadata.getId(), metadata.getFileName(), metadata.getFileSize());

        try{

            metadata.setStatus(DocumentStatus.PROCESSING);
            metadata.setTotalPages(parsedDocs.size());

            //1 converting text of each document in chunks

            //creating text splitter with our configuration
            TokenTextSplitter textSplitter = TokenTextSplitter.builder()
                    .withChunkSize(appProperties.getRag().getChunkSize())
                    .withMinChunkSizeChars(appProperties.getRag().getMinChunkSizeChars())
                    .withMinChunkLengthToEmbed(appProperties.getRag().getMinChunkLengthTOEmbed())
                    .withMaxNumChunks(appProperties.getRag().getMaxNumChunks())
                    .withKeepSeparator(true)
                    .build();

            //sending docs to textSplitter which will return list of documents in chunks form
            List<Document> chunks = textSplitter.apply(parsedDocs);

            //if chunks creation is failed
            if(chunks.isEmpty()){
                metadata.setStatus(DocumentStatus.FAILED);
                metadata.setErrorMessage("Document is empty or unscannable");
                log.info("Chunk creation is failed");
                documentMetadataRepo.save(metadata);
                return 0;
            }

            //now we enrich each chunk with its meta data that means we will attach each chunk with its metadata
            //that we create

            List<Document> enrichedChunks = new ArrayList<>();

            //looping over chunks to attach metdata
            for(int i = 0; i<chunks.size(); i++){

                //creating a hashmap to create chunks meta data
                Map<String, Object> enrichedMetadata = new HashMap<>();
                Document chunk = chunks.get(i);

                //putting meta data in the map
                enrichedMetadata.put("documentId", metadata.getId());
                enrichedMetadata.put("fileName", metadata.getFileName());
                enrichedMetadata.put("contentType", metadata.getContentType());
                enrichedMetadata.put("chunkIndex", i);

                Object pageNumber = chunk.getMetadata().get("page_number");
                if(pageNumber == null){
                    pageNumber = chunk.getMetadata().get("pageNumber");
                }

                if(pageNumber != null){
                    enrichedMetadata.put("pageNumber", pageNumber);
                }

                //new document created with new metadat and chunk
                Document enrichedDoc = new Document(chunk.getText(), enrichedMetadata);
                enrichedChunks.add(enrichedDoc);

            }

            //3 write chunks and embeddings to pg vector
            log.info("Writing {} vector chunks to pgvector store for file: {}",enrichedChunks.size(), metadata.getFileName());

            //adding new chunk list into vectorstore db
            vectorStore.add(enrichedChunks);

            //4. update document status to indexed
            metadata.setStatus(DocumentStatus.INDEXED);
            metadata.setErrorMessage(null);
            documentMetadataRepo.save(metadata);

            return enrichedChunks.size();
        } catch (Exception e) {
            log.error("Failed to ingest document in vector store: {}", metadata.getFileName());
            metadata.setStatus(DocumentStatus.FAILED);
            metadata.setErrorMessage(e.getMessage());
            throw new DocumentProcessingException("Failed to index document: " + e.getMessage() + e);
        }

    }
}
