package com.cbmachinery.aftercareserviceagent;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;
import com.cbmachinery.aftercareserviceagent.auth.repository.UserCredentialRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CbMachineryAfterCareServiceAgentApplication {

	private static final String DEFAULT_ADMIN_USERNAME = "ams.amila@gmail.com";
	private final UserCredentialRepository userCredentialRepository;
	private final PasswordEncoder passwordEncoder;

	public CbMachineryAfterCareServiceAgentApplication(final UserCredentialRepository userCredentialRepository,
			final PasswordEncoder passwordEncoder) {
		super();
		this.userCredentialRepository = userCredentialRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(CbMachineryAfterCareServiceAgentApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		loadDefaultAdmin();
	}

	private void loadDefaultAdmin() {
		userCredentialRepository.findByUsername(DEFAULT_ADMIN_USERNAME)
				.ifPresentOrElse(uc -> log.info("Default admin already exists"), () -> {
					UserCredential savedDefaultAdminCredentials = userCredentialRepository
							.save(UserCredential.builder().active(true).password(passwordEncoder.encode("zaq1xsw2@"))
									.role(Role.ADMIN).username(DEFAULT_ADMIN_USERNAME).build());
					log.info("Default admin added : " + savedDefaultAdminCredentials.getId());

				});
	}

}
