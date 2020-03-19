package com.example.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.model.Department;

@FeignClient(name = "department-service")
public interface DepartmentClient {

	@GetMapping("departments/organizations/{organizationId}")
	public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId);

	@GetMapping("departments/organizations/{organizationId}/employees")
	public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId);

}
