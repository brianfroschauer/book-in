package com.austral.bookin.util;

import com.austral.bookin.exception.InternalServerException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileHandler {

    public static byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new InternalServerException();
        }
    }
}