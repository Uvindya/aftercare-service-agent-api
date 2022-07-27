package com.cbmachinery.aftercareserviceagent.task.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;

public interface MaintainanceService {
	BasicMaintainanceOutputDTO save(MaintainanceInputDTO maintainanceInput);

	Page<BasicMaintainanceOutputDTO> findAll(Pageable pageable, String searchTerm);

	MaintainanceOutputDTO findById(long id);
}
