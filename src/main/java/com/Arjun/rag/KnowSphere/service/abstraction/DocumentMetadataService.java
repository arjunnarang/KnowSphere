package com.Arjun.rag.KnowSphere.service.abstraction;

import com.Arjun.rag.KnowSphere.dto.DocumentResponseDto;
import org.apache.james.mime4j.dom.Multipart;

public interface DocumentMetadataService {
    DocumentResponseDto uploadAndProcess(Multipart file);
}
