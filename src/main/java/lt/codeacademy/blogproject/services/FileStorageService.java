package lt.codeacademy.blogproject.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class FileStorageService {

    @Value("${dir.uploads}")
    private String uploadDir;

    public String save(MultipartFile file) throws IOException {
        String filename = String.format("%s_%d.%s", file.getOriginalFilename().split("\\.")[0], System.currentTimeMillis(), file.getOriginalFilename().split("\\.")[1]);
        file.transferTo(Path.of(uploadDir + "/" + filename));
        return filename;
    }
}
