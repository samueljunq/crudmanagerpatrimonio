package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.Asset;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;
import model.dao.AssetDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/assets", "/asset/form", "/asset/insert", "/asset/update", "/asset/delete" })
public class AssetController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/asset/form": {
			loadCompanies(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-asset.jsp");
			break;
		}
		case "/crud-manager/asset/update": {
			loadAsset(req);
			loadCompanies(req);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-asset.jsp");
			break;
		}
		default:
			listAssets(req);

			ControllerUtil.transferSessionMessagesToRequest(req);

			ControllerUtil.forward(req, resp, "/assets.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/asset/insert": {
			insertAsset(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/assets");
			break;
		}
		case "/crud-manager/asset/update": {
			updateAsset(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/assets");
			break;
		}
		case "/crud-manager/asset/delete": {
			deleteAsset(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/assets");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void deleteAsset(HttpServletRequest req) {
		String assetIdStr = req.getParameter("id");
		int assetId = Integer.parseInt(assetIdStr);

		String assetName = req.getParameter("entityName");

		AssetDAO dao = DAOFactory.createDAO(AssetDAO.class);

		try {
			if (dao.delete(new Asset(assetId))) {
				ControllerUtil.sucessMessage(req, "Ativo '" + assetName + "' excluído com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Ativo '" + assetName + "' não pode ser excluído.");
			}
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertAsset(HttpServletRequest req) {

		Asset asset = createAsset(req, 0);

		AssetDAO dao = DAOFactory.createDAO(AssetDAO.class);

		try {
			if (dao.save(asset))
				ControllerUtil.sucessMessage(req, "Ativo " + asset.getName() + " salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Ativo " + asset.getName() + " não pode ser salvo.");
		} catch (ModelException e) {
			e.printStackTrace(); // log
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateAsset(HttpServletRequest req) {

		String assetIdStr = req.getParameter("asset_id");
		int assetId = Integer.parseInt(assetIdStr);

		Asset asset = createAsset(req, assetId);

		AssetDAO dao = DAOFactory.createDAO(AssetDAO.class);

		try {
			if (dao.update(asset))
				ControllerUtil.sucessMessage(req, "Ativo " + asset.getName() + " alterado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Ativo " + asset.getName() + " não pode ser alterado.");
		} catch (ModelException e) {
			e.printStackTrace(); // log
			ControllerUtil.errorMessage(req, e.getMessage());
		}

	}

	private void loadAsset(HttpServletRequest req) {
		String assetIdStr = req.getParameter("assetId");
		int assetId = Integer.parseInt(assetIdStr);

		AssetDAO dao = DAOFactory.createDAO(AssetDAO.class);

		Asset asset = new Asset(0);

		try {
			asset = dao.findById(assetId);
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar ativo para edição.");
		}

		req.setAttribute("assetToEdit", asset);
	}

	private void loadCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		List<Company> companies = new ArrayList<>();
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar as empresas.");
		}
		req.setAttribute("companies", companies);
	}

	private void listAssets(HttpServletRequest req) {

		AssetDAO dao = DAOFactory.createDAO(AssetDAO.class);

		List<Asset> assets = new ArrayList<>();

		try {
			assets = dao.listAll();
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar dados dos ativos.");
		}

		req.setAttribute("assets", assets);
	}

	private Asset createAsset(HttpServletRequest req, int assetId) {

		String assetName = req.getParameter("asset_name");
		String assetCategory = req.getParameter("asset_category");
		String assetLocation = req.getParameter("asset_location");
		String assetValueStr = req.getParameter("asset_value");
		String assetCompany = req.getParameter("asset_company");
		int assetCompanyId = Integer.parseInt(assetCompany);

		Asset asset;
		if (assetId == 0) {
			asset = new Asset();
		} else {
			asset = new Asset(assetId);
		}
		asset.setName(assetName);
		asset.setCategory(assetCategory);
		asset.setLocation(assetLocation);
		asset.setAcquisitionValue(assetValueStr);
		asset.setCompany(new Company(assetCompanyId));

		return asset;
	}

}
