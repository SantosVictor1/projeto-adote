package br.com.adote.application.service.impl;

import br.com.adote.application.domain.pet.PetImage;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.dto.PetDTO;
import br.com.adote.application.dto.PetImageDTO;
import br.com.adote.application.mapper.PetImageMapper;
import br.com.adote.application.mapper.PetMapper;
import br.com.adote.application.repository.PetImageRepository;
import br.com.adote.application.service.IS3Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class S3ServiceImpl implements IS3Service {
    private final AmazonS3 amazonS3Client;
    private final PetImageRepository petImageRepository;

    @Value("${aws.bucketName}")
    private String bucketName;

    public S3ServiceImpl(AmazonS3 amazonS3Client, PetImageRepository petImageRepository) {
        this.amazonS3Client = amazonS3Client;
        this.petImageRepository = petImageRepository;
    }

    @Override
    public List<PetImageDTO> uploadFile(
        LegalPersonDTO legalPersonDTO, PetDTO petDTO, List<MultipartFile> multipartFiles
    ) throws IOException {
        List<PetImage> petImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            String path = legalPersonDTO.id() + "/" + petDTO.id() + "/" + fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName, path, multipartFile.getInputStream(), null
            );

            amazonS3Client.putObject(putObjectRequest);
            String s3Url = amazonS3Client.getUrl(bucketName, path).toString();

            petImages.add(new PetImage(
                null,
                s3Url,
                fileName,
                PetMapper.toEntity(petDTO, legalPersonDTO),
                null, null
            ));
        }

        petImages = petImageRepository.saveAll(petImages);

        return petImages.stream().map(PetImageMapper::toDto).toList();
    }
}
