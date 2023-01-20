package com.example.onlineresumebuilder.controller;

import com.example.onlineresumebuilder.dtos.CvDto;
import com.example.onlineresumebuilder.exception.InternalException;
import com.example.onlineresumebuilder.payload.response.ApiResponse;
import com.example.onlineresumebuilder.payload.response.PageResponse;
import com.example.onlineresumebuilder.payload.response.ResponseBase;
import com.example.onlineresumebuilder.payload.resquest.AddNewCvRequest;
import com.example.onlineresumebuilder.payload.resquest.GetAllCvRequest;
import com.example.onlineresumebuilder.payload.resquest.UpdateCvRequest;
import com.example.onlineresumebuilder.security.CurrentUser;
import com.example.onlineresumebuilder.security.UserPrincipal;
import com.example.onlineresumebuilder.service.cv.ICvService;
import com.example.onlineresumebuilder.utils.ResponseHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin("*")
@RequestMapping(value="/api/cv", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Cv Controller", description = "Thao tác với Cv")
public class CvController {
    private final ICvService cvService;

    @Operation(
        summary = "Cập nhật thông tin Cv ",
        description = "- Cập nhật thông tin Cv "
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseBase<CvDto>> updateMovie(@Valid @RequestBody UpdateCvRequest request,@CurrentUser UserPrincipal currentUser){
        request.setUserId(currentUser.getId());
        return ResponseEntity.ok(new ResponseBase<>(cvService.updateCv(request)));
    }
    @Operation(
        summary = "Thêm mới Cv ",
        description = "- Thêm mới Cv "
    )
    @PostMapping("/addNew")
    public ResponseEntity<ResponseBase<CvDto>> addNewMovie(@Valid @RequestBody AddNewCvRequest request,@CurrentUser UserPrincipal currentUser){
        request.setUserId(currentUser.getId());
        return ResponseEntity.ok(new ResponseBase<>(cvService.addNewCv(request)));
    }
    @Operation(
        summary = "Xem chi tiết Cv ",
        description = "- Xem chi tiết Cv "
    )
    @GetMapping("/details/{cvId}")
    public ResponseBase<CvDto> getMovieById(@PathVariable Long cvId ){
        return new ResponseBase<>(cvService.getById(cvId));
    }
    @Operation(
        summary = "Tải xuống file Cv PDF ",
        description = "- Tải xuống file Cv PDF "
    )
    @GetMapping("/getPdfCv")
    public ResponseEntity<byte[]> getPdfCv(@RequestParam  Long cvId ){
        HttpHeaders headers = new HttpHeaders();
        ResponseHeaderUtils.setDownloadResponseHeader(headers, "CV_RESUME", ".pdf");

        byte[] excelFile = cvService.exportPdfCv(cvId);

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }
    @Operation(
        summary = "Xóa Cv ",
        description = "- Xóa Cv "
    )
    @DeleteMapping("/{cvId}")
    public ApiResponse deleteMovie(@Valid @PathVariable Long cvId,@CurrentUser UserPrincipal currentUser) {
        if(cvService.deleteCvById(cvId,currentUser.getId()))
            return new ApiResponse(true, "Delete CV Successfully");
        return new ApiResponse(false, "Please check the id");
    }
//    @Operation(
//        summary = "Get All Movie với filter và paging ",
//        description = "- Get All Movie với filter và paging"
//    )
//    @GetMapping("/getAll")
//    public ResponseBase<PageResponse<CvDto>> findAllMoviesPaging(@ParameterObject Pageable pageable, @ParameterObject GetAllMovieRequest request){
//        request.setPageable(pageable);
//        return new ResponseBase<>(cvService.getAllMovie(request));
//    }
    @Operation(
        summary = "Admin: Get All Cv với filter (Page) ",
        description = "- Admin: Get All Cv với filter (Page) "
    )
    @GetMapping("/admin/getPaging")
    public ResponseBase<PageResponse<CvDto>> findAllCvAdmin(@ParameterObject Pageable pageable, @ParameterObject GetAllCvRequest request){
        request.setPageable(pageable);
        return new ResponseBase<>(cvService.findAllCv(request));
    }
    @Operation(
        summary = "Get All Cv với filter (Page) ",
        description = "- Get All Cv với filter (Page) "
    )
    @GetMapping("/getPaging")
    public ResponseBase<PageResponse<CvDto>> findAllCv(@ParameterObject Pageable pageable, @ParameterObject GetAllCvRequest request , @CurrentUser UserPrincipal currentUser){
        request.setPageable(pageable);
        request.setUserId(currentUser.getId());
        return new ResponseBase<>(cvService.findAllCv(request));
    }
    @GetMapping("/dathem")
    public ResponseBase<String> findAllCvaa(){
        return new ResponseBase<>("đa chinh sua va push len ban moi nhat");
    }
}
