package s4c.microservices.users_management.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s4c.microservices.users_management.model.entity.Assets;
import s4c.microservices.users_management.model.entity.Role;
import s4c.microservices.users_management.model.entity.User;
import s4c.microservices.users_management.model.repository.AssetsRepository;

@Service
public class AssetsService implements IAssetsService {

	@Autowired
	private AssetsRepository assetsRepository;


	/**
	 * Returns a complete list of Assets
	 */
	@Override
	public List<Assets> listAssets() {

		return assetsRepository.findAll();

	}


	public Assets getAssetById(long id) {
		return assetsRepository.findOne(id);
	}


	/**
	 * 
	 * @param asset
	 * @return asset
	 */
	public Assets addAsset(Assets asset) {
		//check if parents and childens exist
		ArrayList<Assets> parents2add = new ArrayList<Assets>();
		ArrayList<Assets> childrens2add = new ArrayList<Assets>();
		boolean ok = true;
			
		if(asset.getParents() != null){
			for(Assets parent :  asset.getParents()){
				Assets p = this.assetsRepository.findOne(parent.getId());
				if(p == null) {
					ok = false;
					break;
				} else {
					parents2add.add(p);
				}
			}
		}
		if(asset.getChildrens() != null){
			for(Assets children :  asset.getChildrens()){
				Assets p = this.assetsRepository.findOne(children.getId());
				if(p == null) {
					ok = false;
					break;
				} else {
					childrens2add.add(p);
				}
			}
		}
		
		if(ok) {
			asset.setChildrens(null);
			asset.setParents(null);			
			asset.setChildrens(childrens2add);
			asset.setParents(parents2add);			
			assetsRepository.saveAndFlush(asset);
		} else {
			asset = null;
		}
		
		
		return asset;
	}


	public boolean updateAsset(Assets asset, Assets request) {

		
		if((request.getDescription()!=null)) 
			if(asset.getDescription()==(null))
				asset.setDescription(request.getDescription());
			else if(!asset.getDescription().equals(request.getDescription()))
				asset.setDescription(request.getDescription());

		if((request.getName()!=null))
			if(asset.getName() == null || ((asset.getName() != null) && !request.getName().equals(asset.getName())))
				asset.setName(request.getName());

		if((request.getType()!=null))  
			if(asset.getType() == null || ((asset.getType()!=null) && !request.getType().equals(asset.getType())))
				asset.setType(request.getType());
		
		if(request.getChildrens()!=null){
			asset = getRideOfAssets(asset,request.getChildrens(),false);
		}
		if((asset!=null) && request.getParents()!=null){
			asset = getRideOfAssets(asset,request.getParents(),true);
		}
		
		if(asset != null) {
			assetsRepository.saveAndFlush(asset);
			return true;
		} else {
			return false;
		}
		
	}
	
	
	/**
	 * Update related assets (parent or children ones)
	 * 
	 * @param asset
	 * @param nuevos_assets
	 * @param parent
	 * @return
	 */
	private Assets getRideOfAssets(Assets asset, List<Assets> nuevos_assets, boolean parent) {
		if (nuevos_assets != null) {
			ArrayList<Assets> tAssets = new ArrayList<Assets>();
			List<Assets> tRemove = new ArrayList<Assets>();
			List<Assets> current_assets = new ArrayList<Assets>();
			boolean ok = true;
			if(parent) {
				if(asset.getParents()!=null)
					current_assets =  asset.getParents();
			} else {
				if(asset.getChildrens()!=null)
					current_assets =  asset.getChildrens();
			}
			
			for(Assets asset_n : nuevos_assets){
				try {
					Assets oasset = this.assetsRepository.findOne(asset_n.getId());
					//if exists and is not previously registered on the original, we have to add.
					if((oasset!=null)){
						if (!current_assets.contains(oasset))
							tAssets.add(oasset);	
					} else {
						ok = false;
						break;
					}
				} catch (javax.persistence.EntityNotFoundException e){
					ok = false;
					break;	
				}
			}
			
			if(!ok){
				asset = null;
			} else {
				//if any original asset doesnt appear in nuevos_Assets, we have to remove
				for(Assets asset_original : current_assets){
					boolean found = false;
					for(Assets nasset : nuevos_assets){
						if(nasset.getId().equals(asset_original.getId())){
							found = true;
							break;
						}						
					}
					if(!found)
						tRemove.add(asset_original);
				}

				//add new Assets
				if(!tAssets.isEmpty()){
					for(Assets asset_n : tAssets){
						if(parent)							
							asset.addParent(asset_n);
						else
							asset.addChildren(asset_n);
					}
				}
				
				//remove not referenced assets in the request.
				if(!tRemove.isEmpty()){
					for(Assets asset_n : tRemove){
						if(parent){
							asset.removeParent(asset_n);
						} else {
							asset.removeChildren(asset_n);
						}
					}					
				}
			}
		}
		
		return asset;
	}


	public void deleteAsset(Assets asset) {
		assetsRepository.delete(asset);
		
	}
	
}
