package com.Arjun.rag.KnowSphere.service.impl;

import com.Arjun.rag.KnowSphere.dto.DocumentResponseDto;
import com.Arjun.rag.KnowSphere.entity.DocumentMetadata;
import com.Arjun.rag.KnowSphere.entity.DocumentStatus;
import com.Arjun.rag.KnowSphere.repository.DocumentMetadataRepo;
import com.Arjun.rag.KnowSphere.service.abstraction.DocumentMetadataService;
import lombok.RequiredArgsConstructor;
import org.apache.james.mime4j.dom.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentMetadataServiceImpl implements DocumentMetadataService {

    private final static Logger log = LoggerFactory.getLogger(DocumentMetadataServiceImpl.class);

    private final DocumentMetadataRepo documentMetadataRepo;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public DocumentResponseDto uploadAndProcess(MultipartFile file) {

        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "document";
        String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

        //create entity/object of documentMetadata of file uploaded
        DocumentMetadata documentMetadata = DocumentMetadata.builder()
                .fileName(fileName)
                .contentType(contentType)
                .status(DocumentStatus.UPLOADING)
                .fileSize(file.getSize())
                .build();

        //saving the document meta data
        DocumentMetadata savedDocumentMetadata = documentMetadataRepo.save(documentMetadata);

        //parsing the file
       // List<Document> parsedDocs = parserService.parse(file);

        //ingesting the file
        //int chunksCreated = ingestService.ingest(savedDocumentMetadata, parsedDocs);

        return DocumentResponseDto.builder()
                .id(savedDocumentMetadata.getId())
                .fileName(fileName)
                .fileSize(savedDocumentMetadata.getFileSize())
                .status(documentMetadata.getStatus())
                .message("Documents uploaded and processed")
                .build();
    }

}
