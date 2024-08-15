package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Asset;
import model.Company;
import model.ModelException;

public class MySQLAssetDAO implements AssetDAO {

	@Override
	public boolean save(Asset asset) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO asset VALUES (DEFAULT, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);

		db.setString(1, asset.getName());
		db.setString(2, asset.getCategory());
		db.setString(3, asset.getLocation());
		db.setString(4, asset.getAcquisitionValue());
		db.setInt(5, asset.getCompany().getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Asset asset) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE asset SET name = ?, category = ?, location = ?, acquisitionValue = ?, company = ? WHERE id = ?;";

		db.prepareStatement(sqlUpdate);

		db.setString(1, asset.getName());
		db.setString(2, asset.getCategory());
		db.setString(3, asset.getLocation());
		db.setString(4, asset.getAcquisitionValue());
		db.setInt(5, asset.getCompany().getId());
		db.setInt(6, asset.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Asset asset) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = "DELETE FROM asset WHERE id = ?";

		db.prepareStatement(sqlDelete);

		db.setInt(1, asset.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public Asset findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM asset WHERE id = ?;";

		db.prepareStatement(sql);

		db.setInt(1, id);
		db.executeQuery();

		Asset asset = null;

		while (db.next()) {
			asset = createAsset(db);
			break;
		}

		return asset;
	}

	@Override
	public List<Asset> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Asset> assets = new ArrayList<>();

		String sqlQuery = "SELECT a.id as ID, a.name AS name, a.category AS category, a.location AS location, a.acquisitionValue AS acquisitionValue, cp.id as company from asset a inner join companies cp on a.company = cp.id;";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			Asset asset = createAsset(db);

			assets.add(asset);
		}

		return assets;
	}

	private Asset createAsset(DBHandler db) throws ModelException {
		Asset asset = new Asset(db.getInt("id"));
		asset.setName(db.getString("name"));
		asset.setCategory(db.getString("category"));
		asset.setLocation(db.getString("location"));
		asset.setAcquisitionValue(db.getString("acquisitionValue"));

		CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class);

		Company company = companyDAO.findById(db.getInt("company"));

		asset.setCompany(company);

		return asset;
	}
}
