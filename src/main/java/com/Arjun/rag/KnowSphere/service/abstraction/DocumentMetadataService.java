package com.Arjun.rag.KnowSphere.service.abstraction;

import com.Arjun.rag.KnowSphere.dto.DocumentResponseDto;
import org.apache.james.mime4j.dom.Multipart;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentMetadataService {
    DocumentResponseDto uploadAndProcess(MultipartFile file);
}
