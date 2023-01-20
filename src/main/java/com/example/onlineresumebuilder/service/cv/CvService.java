package com.example.onlineresumebuilder.service.cv;

import com.example.onlineresumebuilder.dtos.CvDto;
import com.example.onlineresumebuilder.enums.ResponseCode;
import com.example.onlineresumebuilder.exception.InternalException;
import com.example.onlineresumebuilder.model.Cv;
import com.example.onlineresumebuilder.model.User;
import com.example.onlineresumebuilder.payload.response.PageResponse;
import com.example.onlineresumebuilder.payload.resquest.AddNewCvRequest;
import com.example.onlineresumebuilder.payload.resquest.GetAllCvRequest;
import com.example.onlineresumebuilder.payload.resquest.UpdateCvRequest;
import com.example.onlineresumebuilder.repository.CvRepository;
import com.example.onlineresumebuilder.repository.UserRepository;
import com.example.onlineresumebuilder.utils.CvPdfExporter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CvService implements ICvService{

    private final UserRepository userRepository;

    private final CvRepository cvRepository;

    private final ModelMapper modelMapper;

    @Override
    public CvDto addNewCv(AddNewCvRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));
        Cv cv = Cv.builder()
            .information(request.getInformation())
            .summary(request.getSummary())
            .experiences(request.getExperiences())
            .education(request.getEducation())
            .skills(request.getSkills())
            .user(user)
            .build();
        return modelMapper.map(cvRepository.save(cv),CvDto.class);
    }

    @Override
    public CvDto updateCv(UpdateCvRequest request) {
        Cv cv = cvRepository.findById(request.getCvId()).orElseThrow(() -> new InternalException(ResponseCode.CV_NOT_FOUND));
        if(!request.getSummary().isBlank()) cv.setSummary(request.getSummary());
        if(!request.getSkills().isBlank()) cv.setSkills(request.getSkills());
        if(!request.getInformation().isBlank()) cv.setInformation(request.getInformation());
        if(!request.getExperiences().isBlank()) cv.setExperiences(request.getExperiences());
        if(!request.getEducation().isBlank()) cv.setEducation(request.getEducation());
        return modelMapper.map(cvRepository.save(cv),CvDto.class);
    }

    @Override
    public PageResponse<CvDto> findAllCv(GetAllCvRequest request) {
        Page<Cv> moviePage = cvRepository.findAll(request.getSpecification(), request.getPageable());
        return new PageResponse<>(moviePage.map(CvDto::new));
    }

    @Override
    public CvDto getById(Long id) {
        Cv cv = cvRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.CV_NOT_FOUND));
        return modelMapper.map(cvRepository.save(cv),CvDto.class);
    }

    @Override
    public Boolean deleteCvById(Long id,Long userId) {
        try {
            cvRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.CV_NOT_FOUND));
            GetAllCvRequest request = new GetAllCvRequest();
            request.setUserId(userId);
            request.setId(id);
            List<Cv> list = cvRepository.findAll(request.getSpecification());
            if(list.isEmpty()){
                throw new InternalException(ResponseCode.CV_NOT_FOUND);
            }
            cvRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public byte[] exportPdfCv(Long id) {
        Cv cv = cvRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.CV_NOT_FOUND));
        CvPdfExporter pdfExporter = new CvPdfExporter(cv);
        return pdfExporter.export();
    }
}
