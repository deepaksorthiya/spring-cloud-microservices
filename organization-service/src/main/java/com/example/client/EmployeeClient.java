package com.example.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.model.Employee;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

	@GetMapping("employees/organizations/{organizationId}")
	List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId);

}
