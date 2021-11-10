package com.vti.service;

import java.util.List;

import com.vti.entity.Position;
import com.vti.form.PositionFormForCreating;
import com.vti.form.PositionFormForUpdating;

public interface IPositionService {
	public List<Position> getAllPositions();

	public Position getPositionByID(short id);

	public void createPosition(PositionFormForCreating pos);

	public void updatePosition(short id, PositionFormForUpdating pos);

	public void deletePosition(short id);

	public boolean isPositionExistsByID(short id);

}