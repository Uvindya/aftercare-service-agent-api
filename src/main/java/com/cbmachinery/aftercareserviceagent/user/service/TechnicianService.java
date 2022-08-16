package com.cbmachinery.aftercareserviceagent.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public interface TechnicianService {
	BasicUserOutputDTO save(TechnicianInputDTO technicianInput);

	Page<BasicUserOutputDTO> findAll(Pageable pageable, String searchTerm);

	TechnicianOutputDTO findById(long id);

	Technician findByIdAsRaw(long id);

	void importFromCSV(MultipartFile csv);

	List<BasicUserOutputDTO> findAll();
}
