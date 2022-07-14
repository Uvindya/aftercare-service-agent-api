package com.cbmachinery.aftercareserviceagent.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientInputDTO;

public interface ClientService {
	BasicUserOutputDTO save(ClientInputDTO clientInput);

	Page<BasicUserOutputDTO> findAll(Pageable pageable, String searchTerm);
}
