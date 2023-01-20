package com.example.onlineresumebuilder.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseHeaderUtils {

    public static void setDownloadResponseHeader(HttpHeaders headers, String prefix, String extension) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormatter.format(new Date());
        String fileName = prefix + "_" + timestamp + extension;

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        String headerKey = HttpHeaders.CONTENT_DISPOSITION;
        String headerValue = "attachment; filename=" + fileName;
        headers.add(headerKey, headerValue);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    }
}
