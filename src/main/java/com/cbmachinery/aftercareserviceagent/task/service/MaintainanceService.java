package com.cbmachinery.aftercareserviceagent.task.service;

import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceInputDTO;

public interface MaintainanceService {
	BasicMaintainanceOutputDTO save(MaintainanceInputDTO maintainanceInput);
}
