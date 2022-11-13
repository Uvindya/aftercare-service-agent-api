package com.cbmachinery.aftercareserviceagent.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.user.model.Administrator;
import com.cbmachinery.aftercareserviceagent.user.repository.AdministratorRepository;
import com.cbmachinery.aftercareserviceagent.user.service.AdministartorService;

@Service
public class AdministartorServiceImpl implements AdministartorService {

	private final AdministratorRepository administratorRepository;

	public AdministartorServiceImpl(final AdministratorRepository administratorRepository) {
		super();
		this.administratorRepository = administratorRepository;
	}

	@Override
	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

}
