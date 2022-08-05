package com.cbmachinery.aftercareserviceagent.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.Client;

public interface ClientService {
	BasicUserOutputDTO save(ClientInputDTO clientInput);

	Page<BasicUserOutputDTO> findAll(Pageable pageable, String searchTerm);

	ClientOutputDTO findByIdAsDTO(long id);

	Client findById(long id);

	List<BasicUserOutputDTO> findAll();

	void importFromCSV(MultipartFile csv);

}
