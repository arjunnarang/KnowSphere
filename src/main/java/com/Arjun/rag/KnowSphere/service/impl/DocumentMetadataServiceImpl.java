package com.Arjun.rag.KnowSphere.service.impl;

import com.Arjun.rag.KnowSphere.dto.DocumentResponseDto;
import com.Arjun.rag.KnowSphere.service.abstraction.DocumentMetadataService;
import org.apache.james.mime4j.dom.Multipart;
import org.springframework.stereotype.Service;

@Service
public class DocumentMetadataServiceImpl implements DocumentMetadataService {
    @Override
    public DocumentResponseDto uploadAndProcess(Multipart file) {
        return null;
    }
}
