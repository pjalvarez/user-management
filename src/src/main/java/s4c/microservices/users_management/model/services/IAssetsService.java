package s4c.microservices.users_management.model.services;

import java.util.List;

import s4c.microservices.users_management.model.entity.Assets;

public interface IAssetsService {
	public List<Assets> listAssets();
}
