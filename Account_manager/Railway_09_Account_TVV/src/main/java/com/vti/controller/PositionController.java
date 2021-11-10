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

import com.vti.dto.DepartmentDto;
import com.vti.dto.PositionDto;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.form.PositionFormForCreating;
import com.vti.form.PositionFormForUpdating;
import com.vti.service.IPositionService;

@RestController
@RequestMapping(value = "api/v1/positions")
@CrossOrigin("*")
public class PositionController {
	@Autowired
	private IPositionService positionService;

	@GetMapping
	public ResponseEntity<?> getAllPositions() {
		List<Position> entities = positionService.getAllPositions();
		List<PositionDto> posdto = new ArrayList<>();
		for (Position entity : entities) {
			PositionDto dto = new PositionDto(entity.getId(), entity.getName().toString());
			posdto.add(dto);
		}
		return new ResponseEntity<>(posdto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getPositionByID(@PathVariable(name = "id") short id) {
		Position pos = positionService.getPositionByID(id);
		PositionDto depdto = new PositionDto(pos.getId(), pos.getName().toString());
		return new ResponseEntity<PositionDto>(depdto, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> createPosition(@RequestBody PositionFormForCreating pos) {
		positionService.createPosition(pos);
		return new ResponseEntity<String>("Create successfully!", HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updatePosition(@PathVariable(name = "id") short id, @RequestBody PositionFormForUpdating pos) {
		positionService.updatePosition(id, pos);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePosition(@PathVariable(name = "id") short id) {
		positionService.deletePosition(id);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
	}
}
