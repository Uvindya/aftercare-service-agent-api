package com.cbmachinery.aftercareserviceagent.util;

import java.time.LocalDate;
import java.util.stream.IntStream;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceInputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceType;
import com.cbmachinery.aftercareserviceagent.task.service.MaintainanceService;

@Component
public class ProductMaintainanceUtil {

	private final MaintainanceService maintainanceService;

	public ProductMaintainanceUtil(final MaintainanceService maintainanceService) {
		super();
		this.maintainanceService = maintainanceService;
	}

	@Async
	public void scheduleMaintainances(long productId, LocalDate purchasedAt, int warrentyPeriod,
			int maintainnanceInterval) {
		int numOfMaintainances = warrentyPeriod / maintainnanceInterval;

		IntStream.range(1, numOfMaintainances + 1).boxed().forEach(i -> {
			LocalDate scheduledAt = purchasedAt.plusMonths(i * maintainnanceInterval);
			MaintainanceInputDTO maintainanceInput = new MaintainanceInputDTO(
					String.format("Regular Maintainance %d", i), scheduledAt, productId, MaintainanceType.REGULAR);
			maintainanceService.save(maintainanceInput);
		});

	}

}
