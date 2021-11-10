package com.vti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Position;
import com.vti.form.PositionFormForCreating;
import com.vti.form.PositionFormForUpdating;
import com.vti.repository.IPositionRepository;

@Service
public class PositionService implements IPositionService {
	@Autowired
	private IPositionRepository positionRepository;

	@Override
	public List<Position> getAllPositions() {
		return positionRepository.findAll();
	}

	@Override
	public Position getPositionByID(short id) {
		return positionRepository.findById(id).get();
	}

	@Override
	public void createPosition(PositionFormForCreating pos) {
		Position position = new Position(pos.getName());
		positionRepository.save(position);
	}

	@Override
	public void updatePosition(short id, PositionFormForUpdating pos) {
		Position position = getPositionByID(id);
		position.setName(pos.getName());
		positionRepository.save(position);
	}

	@Override
	public void deletePosition(short id) {
		positionRepository.deleteById(id);
	}

	@Override
	public boolean isPositionExistsByID(short id) {
		return positionRepository.existsById(id);
	}

}
