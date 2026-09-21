package com.Arjun.rag.KnowSphere.service.impl;

import com.Arjun.rag.KnowSphere.exception.DocumentProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentParserService {

    private static final Logger log = LoggerFactory.getLogger(DocumentParserService.class);

    public List<Document> parse(MultipartFile file) {

        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "document";

        String contentType = file.getContentType() != null ? file.getContentType() : "";

        log.info("Parsing document: {}, size: {}, content type: {}", fileName, file.getSize(), contentType);

        //from here creation of parsed document starts
        try{

            Resource resource = new ByteArrayResource(file.getBytes()){

                @Override
                public String getFilename(){
                    return fileName;
                }
            };

            if(fileName.toLowerCase().endsWith(".pdf") || contentType.contains("pdf")){
                return parsePdf(resource);
            }
            else{
                return parseGenericFile(resource);
            }


        } catch(Exception ex){
            log.error("Doocument processing failed: {}", fileName);

            throw new DocumentProcessingException("Failed to process document: " + fileName, ex);
        }
    }

    //here other formats of file is read and parsed by PagePdfDocumentReader which returns list of 'Document'
    //in parsed way
    private List<Document> parseGenericFile(Resource resource) {

        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageBottomMargin(0)
                .build();

        PagePdfDocumentReader documentReader = new PagePdfDocumentReader(resource, config);

        return documentReader.read();
    }

    //here pdf file is read and parsed by TikaDocumentReader which returns list of 'Document'
    //in parsed way
    private List<Document> parsePdf(Resource resource) {
        TikaDocumentReader documentReader = new TikaDocumentReader(resource);

        return documentReader.read();
    }
}
