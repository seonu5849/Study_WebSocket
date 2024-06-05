package com.example.websocket.file.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    private static String LOCAL_PROFILE_DIR;

    @Value("${file.upload.path.profileImg}")
    public void setProfileDir(String profileDir) {
        LOCAL_PROFILE_DIR = profileDir;
    }

    // 확장자를 가져오는 메소드
    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    // 파일 이름만을 가져오는 메소드
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    // UUID값을 파일이름에 붙여줘서 가져와주는 메소드
    public static String getFileNameWithUUID(String fileName) {
        return UUID.randomUUID().toString() + "_" +fileName;
    }

    // 정적 팩토리 메소드
    public static String getFullPath(String fileName) {
        return LOCAL_PROFILE_DIR + fileName;
    }

    // 파일을 저장하는 메소드
    public static String getMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        String fileNameWithUUID = UUID.randomUUID().toString() +"."+ getExtension(multipartFile.getOriginalFilename());
        File file = new File(LOCAL_PROFILE_DIR, fileNameWithUUID);
        multipartFile.transferTo(file);
        return fileNameWithUUID;
    }
}
