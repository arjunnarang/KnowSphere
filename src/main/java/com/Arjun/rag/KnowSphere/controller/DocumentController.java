package com.Arjun.rag.KnowSphere.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents")

//this tag is used to describe name and description of section "Document Management" in open ai url
@Tag(
        name="Document Management",
        description="This section is used for uploading, listing and creation of document for vector embeddings"
)
public class DocumentController {

    @PostMapping
    @Operation(  //this is used to describe summary and description about particular end point in open ai url
            summary = "Upload any document from the following: (DOCX, PDF, CSV, MD, TEXT)",
            description = "This endpoint is used to upload documents to index and create vectors and embeddings"
    )
    public ResponseEntity<String> uploadDocument(){
        return ResponseEntity.ok("Uploaded");
    }
}
