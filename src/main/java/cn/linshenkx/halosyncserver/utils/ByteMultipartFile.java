package cn.linshenkx.halosyncserver.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ByteMultipartFile implements MultipartFile {

    private String fileName;
    private byte[] content;
    private String ext;

    public ByteMultipartFile() {
    }

    public ByteMultipartFile(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    public ByteMultipartFile(String fileName, byte[] content, String ext) {
        this.fileName = fileName;
        this.content = content;
        this.ext = ext;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return ext;
    }

    @Override
    public boolean isEmpty() {
        return content == null || content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream f = new FileOutputStream(dest)) {
            f.write(content);
        }
    }
}
