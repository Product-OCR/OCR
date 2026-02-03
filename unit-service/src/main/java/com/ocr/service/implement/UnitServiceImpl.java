package com.ocr.service.implement;

import com.ocr.dto.request.CreateUnitRequest;
import com.ocr.dto.request.UpdateUnitRequest;
import com.ocr.dto.response.*;
import com.ocr.enums.ErrorCode;
import com.ocr.exception.BaseException;
import com.ocr.model.Unit;
import com.ocr.repository.UnitRepository;
import com.ocr.service.UnitService;
import com.ocr.utils.UnitUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final Logger logger = LoggerFactory.getLogger(UnitServiceImpl.class);
    private final UnitRepository unitRepository;

    @Override
    public ApiResponse<CreateUnitResponse> createUnit(CreateUnitRequest request) {

        if (unitRepository.existsUnitByUnitName(request.getUnitName()))
        {
            logger.error("Unit name {} is already in use.", request.getUnitName());
            throw new BaseException(ErrorCode.UNIT_ALREADY_EXISTS);
        }

        UnitUtil util = new UnitUtil();

        Unit unit = getUnit(request);

        Unit newUnit = unitRepository.save(unit);

        newUnit.setUnitCode(util.generateUnitCode(newUnit.getId()));

        // set số lượng projects ban đầu là 0
        newUnit.setTotalProjects(0);
        newUnit = unitRepository.save(newUnit);

        CreateUnitResponse response = getCreateUnitResponse(unit, newUnit);

        return ApiResponse.<CreateUnitResponse>builder()
                .result(response)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();

    }

    private Unit getUnit(CreateUnitRequest request) {
        Unit unit = Unit.builder()
                .unitName(request.getUnitName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .address(request.getAddress())
                .contactPerson(request.getContactPerson())
                .contactNumber(request.getContactNumber())
                .build();
        return unit;
    }

    private CreateUnitResponse getCreateUnitResponse(Unit unit, Unit newUnit) {
        CreateUnitResponse response = CreateUnitResponse.builder()
                .id(unit.getId())
                .unitCode(newUnit.getUnitCode())
                .unitName(newUnit.getUnitName())
                .startDate(newUnit.getStartDate())
                .endDate(newUnit.getEndDate())
                .address(newUnit.getAddress())
                .contactPerson(newUnit.getContactPerson())
                .contactNumber(newUnit.getContactNumber())
                .build();
        return response;
    }

    @Override
    public ApiResponse<UpdateUnitResponse> updateUnit(Long unitId,UpdateUnitRequest request) {

        logger.info("Updating unit {}", request.getUnitName());

        if (!unitRepository.existsUnitById(unitId)){
            logger.error("Unit id {} does not exist.", unitId);
            throw new BaseException(ErrorCode.UNIT_NOT_FOUND);
        }

        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> {
            logger.error("Unit id {} not found.", unitId);
            throw new BaseException(ErrorCode.UNIT_NOT_FOUND);
        });

        if(unitRepository.existsUnitByUnitName(request.getUnitName()) && request.getUnitName() != unit.getUnitName() ){
            logger.error("Unit name {} is already in use.", request.getUnitName());
            throw new BaseException(ErrorCode.UNIT_ALREADY_EXISTS);
        }

        unit.setUnitName(request.getUnitName());
        unit.setStartDate(request.getStartDate());
        unit.setEndDate(request.getEndDate());
        unit.setAddress(request.getAddress());
        unit.setContactPerson(request.getContactPerson());
        unit.setContactNumber(request.getContactNumber());

        unitRepository.save(unit);

        UpdateUnitResponse response = getUpdateUnitResponse(unit);

        return ApiResponse.<UpdateUnitResponse> builder()
                .result(response)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    private UpdateUnitResponse getUpdateUnitResponse(Unit unit) {
        UpdateUnitResponse response = UpdateUnitResponse.builder()
                .id(unit.getId())
                .unitCode(unit.getUnitCode())
                .unitName(unit.getUnitName())
                .startDate(unit.getStartDate())
                .endDate(unit.getEndDate())
                .address(unit.getAddress())
                .contactPerson(unit.getContactPerson())
                .contactNumber(unit.getContactNumber())
                .build();
        return response;
    }

    @Override
    public ApiResponse<DeleteUnitResponse> deleteUnit(Long unitId) {
        logger.info("Deleting unit {}", unitId);

        if (!unitRepository.existsUnitById(unitId)){
            logger.error("Unit id {} does not exist.", unitId);
            throw new BaseException(ErrorCode.UNIT_NOT_FOUND);
        }
        unitRepository.deleteById(unitId);
        return ApiResponse.<DeleteUnitResponse> builder()
                .result(null)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    @Override
    public ApiResponse<GetUnitResponse> getUnit(Long unitId) {
        logger.info("Retrieving unit {}", unitId);

        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> {
            logger.error("Unit id {} not found.", unitId);
            throw new BaseException(ErrorCode.UNIT_NOT_FOUND);
        });

        GetUnitResponse response = getUnitResponse(unit);

        return ApiResponse.<GetUnitResponse> builder()
                .result(response)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .build();
    }

    private GetUnitResponse getUnitResponse(Unit unit) {
        GetUnitResponse response = GetUnitResponse.builder()
                .id(unit.getId())
                .unitCode(unit.getUnitCode())
                .unitName(unit.getUnitName())
                .startDate(unit.getStartDate())
                .endDate(unit.getEndDate())
                .address(unit.getAddress())
                .contactPerson(unit.getContactPerson())
                .contactNumber(unit.getContactNumber())
                .totalProjects(unit.getTotalProjects())
                .build();
        return response;
    }
}
