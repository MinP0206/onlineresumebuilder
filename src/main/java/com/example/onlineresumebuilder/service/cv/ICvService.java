package com.example.onlineresumebuilder.service.cv;

import com.example.onlineresumebuilder.dtos.CvDto;
import com.example.onlineresumebuilder.payload.response.PageResponse;
import com.example.onlineresumebuilder.payload.resquest.AddNewCvRequest;
import com.example.onlineresumebuilder.payload.resquest.GetAllCvRequest;
import com.example.onlineresumebuilder.payload.resquest.UpdateCvRequest;



public interface ICvService {
    CvDto addNewCv(AddNewCvRequest request);
    CvDto updateCv(UpdateCvRequest request);
    PageResponse<CvDto> findAllCv(GetAllCvRequest request);
    CvDto getById(Long id);

    Boolean deleteCvById(Long id,Long userId);

    byte[] exportPdfCv(Long id);

}
