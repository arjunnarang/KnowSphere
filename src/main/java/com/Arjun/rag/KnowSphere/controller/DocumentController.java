package com.Arjun.rag.KnowSphere.controller;

import com.Arjun.rag.KnowSphere.service.abstraction.DocumentMetadataService;
import com.Arjun.rag.KnowSphere.dto.ApiResponse;
import com.Arjun.rag.KnowSphere.dto.DocumentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.james.mime4j.dom.Multipart;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/documents")

//this tag is used to describe name and description of section "Document Management" in open ai url
@Tag(
        name="Document Management",
        description="This section is used for uploading, listing and creation of document for vector embeddings"
)
public class DocumentController {


    private final DocumentMetadataService documentMetadataService;

    public DocumentController(DocumentMetadataService documentMetadataService) {
        this.documentMetadataService = documentMetadataService;
    }

    //this means this end point consimes multipart type data
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(  //this is used to describe summary and description about particular end point in open ai url
            summary = "Upload any document from the following: (DOCX, PDF, CSV, MD, TEXT)",
            description = "This endpoint is used to upload documents to index and create vectors and embeddings"
    )
    public ResponseEntity<ApiResponse<DocumentResponseDto>> uploadDocument(
            @RequestParam("file") Multipart file
    ){

        DocumentResponseDto documentResponseDto = this.documentMetadataService.uploadAndProcess(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<DocumentResponseDto>builder()
                        .success(true)
                        .data(documentResponseDto)
                        .timeStamp(LocalDateTime.now())
                        .message("Document uploaded successfully")
                        .build()
        );
    }
}
