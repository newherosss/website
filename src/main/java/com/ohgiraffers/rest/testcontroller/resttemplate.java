package com.ohgiraffers.rest.testcontroller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;


@RestController
public class resttemplate {
    private final String url = "http://192.168.0.88:5000/submit";
    @Value("${file.dir}")
    private String fileDir;

    private String getBase64String(MultipartFile multipartFile) throws Exception {
        byte[] bytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    @GetMapping("/posttest")
    public FlaskResponseDto requestToFlask(String fileName, MultipartFile file) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Body set
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String imageFileString = getBase64String(file);
        body.add("filename", fileName);
        body.add("image", imageFileString);
        // Message
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
        // Request
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);
        // Response 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        FlaskResponseDto dto = objectMapper.readValue(response.getBody(), FlaskResponseDto.class);
        return dto;
    }

    @PostMapping("/posttester")
    public RedirectView requestToFlasker(String fileName, HttpServletResponse responser, @RequestParam("file") MultipartFile files) throws Exception {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000000);
        factory.setReadTimeout(10000000);
        factory.setBufferRequestBody(false); // 파일 전송은 이 설정을 꼭 해주자.
        System.out.println(files.getOriginalFilename());
        RestTemplate restTemplate = new RestTemplate(factory);
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // Body set
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        String savedPath = fileDir + files.getOriginalFilename();
        files.transferTo(new File(savedPath));
        Resource resource1 = new FileSystemResource(savedPath);

        body.add("file", resource1);
        body.add("txt","test");
        System.out.println(savedPath);
        // Message
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
        // Request
        System.out.println(requestEntity);
        // Request
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        // Response 파싱
        if (response.getStatusCode() == HttpStatus.OK) {
            // 서버로부터 파일 데이터를 받음
            byte[] fileData = response.getBody();
            String uuid = UUID.randomUUID().toString();
            // 확장자 추출(ex : .png)
            String extension = ".png";
            // uuid와 확장자 결합
            String savedName = uuid + extension;
            // 받은 파일 데이터를 저장할 경로 설정
            String receivedFilePath = fileDir + savedName;
            // 받은 파일 데이터를 저장할 경로 설정
            // 파일 저장
            try (FileOutputStream fos = new FileOutputStream(receivedFilePath)) {
                fos.write(fileData);
            }
            // Flask 서버로부터 받은 응답 데이터를 사용해야 한다면 해당 데이터를 사용
            String responseBody = new String(fileData, StandardCharsets.UTF_8);
            System.out.println("Received Response: " + responseBody);
            return new RedirectView("view2");

        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return new RedirectView("view2");
        }
    }

    // Content-Disposition 헤더에서 파일 이름을 추출하는 메서드
    private String extractFileNameFromContentDisposition(String contentDisposition) {
        if (contentDisposition != null && contentDisposition.contains("filename=")) {
            String[] parts = contentDisposition.split(";");
            for (String part : parts) {
                if (part.trim().startsWith("filename=")) {
                    return part.substring("filename=".length()).trim();
                }
            }
        }
        return null; // 파일 이름을 찾을 수 없는 경우
    }


}
