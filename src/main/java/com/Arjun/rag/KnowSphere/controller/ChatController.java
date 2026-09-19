package com.Arjun.rag.KnowSphere.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/chat")
@RestController
@Tag(//this tag is used to describe name and description of section "Chat Management" in open ai url
        name = "Chat Management",
        description = "Chat management endpoints are here in this section"
)
public class ChatController {

    @PostMapping
    @Operation( //this annotation is used to describe summary and description about particular end point in open ai url
            summary = "This end point takes chat",
            description = "This end point takes chat and manages it"
    )
    public ResponseEntity<String> chat(){
        return ResponseEntity.ok("Oka");
    }
}
