package de.supercode.superBnB.servicies;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.supercode.superBnB.entities.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ImgBBService {

    @Value("${imgbb.api.key}")
    private String imgbbApiKey;

    private final RestTemplate restTemplate;

    private PropertyService propertyService;

    @Autowired
    public ImgBBService(RestTemplate restTemplate, PropertyService propertyService) {
        this.restTemplate = restTemplate;
        this.propertyService = propertyService;
    }

    public String uploadImage(MultipartFile file, long propertyId) throws IOException {
        String url = "https://api.imgbb.com/1/upload?key=" + imgbbApiKey;
        // Convert img in base64
        return getImageUrl(file, propertyId, url);


    }

    public List<String> uploadMoreImages(MultipartFile[] files, long propertyId) throws IOException {
        String url = "https://api.imgbb.com/1/upload?key=" + imgbbApiKey;
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            // Convert img in base64
            String imageUrl = getImageUrl(file, propertyId, url);
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    private String getImageUrl(MultipartFile file, long propertyId, String url) throws IOException {
        String encodedImage = Base64.getEncoder().encodeToString(file.getBytes());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", encodedImage);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        //POST
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String imageUrl = extractImageUrl(response.getBody());
        propertyService.saveNewImage(propertyId, imageUrl);
        return imageUrl;
    }



    public String extractImageUrl(String jsonResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        return rootNode.path("data").path("url").asText();
    }
}
