package com.vti.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDto;
import com.vti.dto.DepartmentDto;
import com.vti.entity.Department;
import com.vti.form.DepartmentFormForCreating;
import com.vti.form.DepartmentFormForUpdating;
import com.vti.service.IDepartmentService;

@RestController
@RequestMapping(value = "api/v1/departments")
@CrossOrigin("*")
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;

	@GetMapping
	public ResponseEntity<?> getAllDepartments() {
		List<Department> entities = departmentService.getAllDepartments();
		List<DepartmentDto> depdto = new ArrayList<>();
		for (Department entity : entities) {
			DepartmentDto dto = new DepartmentDto(entity.getId(), entity.getName());
			depdto.add(dto);
		}
		return new ResponseEntity<>(depdto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getDepartmentByID(@PathVariable(name = "id") short id) {
		Department dep = departmentService.getDepartmentByID(id);
		DepartmentDto depdto = new DepartmentDto(dep.getId(), dep.getName());
		return new ResponseEntity<DepartmentDto>(depdto, HttpStatus.OK);
	}

	@GetMapping(value = "/name/{name}")
	public ResponseEntity<?> getDepartmentByName(@PathVariable(name = "name") String name) {
		Department dep = departmentService.getDepartmentByName(name);
		DepartmentDto depdto = new DepartmentDto(dep.getId(), dep.getName());
		return new ResponseEntity<DepartmentDto>(depdto, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> createDepartment(@RequestBody DepartmentFormForCreating dep) {

		departmentService.createDepartment(dep);
		return new ResponseEntity<String>("Create successfully!", HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateDepartment(@PathVariable(name = "id") short id,
			@RequestBody DepartmentFormForUpdating dep) {
		departmentService.updateDepartment(id, dep);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable(name = "id") short id) {
		departmentService.deleteDepartment(id);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
	}
}
