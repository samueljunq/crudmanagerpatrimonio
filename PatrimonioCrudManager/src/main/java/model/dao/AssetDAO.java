package model.dao;

import java.util.List;

import model.Asset;
import model.ModelException;

public interface AssetDAO {
	boolean save(Asset asset) throws ModelException;

	boolean update(Asset asset) throws ModelException;

	boolean delete(Asset asset) throws ModelException;

	List<Asset> listAll() throws ModelException;

	Asset findById(int id) throws ModelException;
}
