package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.EmployeeClient;
import com.example.model.Department;
import com.example.model.Employee;
import com.example.repository.DepartmentRepository;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	EmployeeClient employeeClient;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Department add(@RequestBody Department department) {
		LOGGER.info("Department add: {}", department);
		return departmentRepository.add(department);
	}

	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Department findById(@PathVariable("id") Long id) {
		LOGGER.info("Department find: id={}", id);
		return departmentRepository.findById(id);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Department> findAll() {
		LOGGER.info("Department find");
		return departmentRepository.findAll();
	}

	@GetMapping("/organizations/{organizationId}")
	public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		return departmentRepository.findByOrganization(organizationId);
	}

	@GetMapping("/organizations/{organizationId}/employees")
	public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		List<Department> departments = departmentRepository.findByOrganization(organizationId);
		for (Department d : departments) {
			List<Employee> emps = employeeClient.findByDepartment(d.getId());
			d.setEmployees(emps);
		}
		return departments;
	}

}
