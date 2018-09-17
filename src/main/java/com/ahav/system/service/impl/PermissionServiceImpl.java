package com.ahav.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahav.system.dao.PermissionMapper;
import com.ahav.system.dao.RolePermissionMapper;
import com.ahav.system.entity.Permission;
import com.ahav.system.service.PermissionService;
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper perDao;
	
	@Autowired
	private RolePermissionMapper rolePerDao;

	@Override
	public List<Permission> findPermissions() {
		List<Permission> pers = perDao.selectPermissions();
		List<Permission> result = analysisPermission(pers);
		return result;
	}

	@Override
	public List<Permission> findPermissionByRoleId(Integer roleId) {
		List rps = rolePerDao.selectPermissionIds(roleId);
		if(rps.size()==0){
			return null;
		}else{
			List perIds = new ArrayList<>();
			for(int n=0; n<rps.size(); n++){
				perIds.add(rps.get(n));
			}
			List<Permission> pers = perDao.selectPermissionByPermissionIds(perIds);
//			pers = analysisPermission(pers);
			return pers;
		}
	}
	
	/**
	 * 把权限集合按照层级分组的解析方法
	 * @param pers
	 * @return
	 */
	private List<Permission> analysisPermission(List<Permission> pers){
		
		List<Permission> pers1 = new ArrayList<>();
		
		for(int i=0; i<pers.size(); i++){
			if(pers.get(i).getParentPermissionId() == 0){
				pers1.add(pers.get(i));
			}
		}
		pers.remove(pers1);
		for(int i=0; i<pers1.size(); i++){
			List<Permission> pers2 = new ArrayList<>();
			for(int j=0; j<pers.size(); j++){
				if(pers1.get(i).getPermissionId().equals(pers.get(j).getParentPermissionId())){
					pers2.add(pers.get(j));
				}
			}
			pers.remove(pers2);
			pers1.get(i).setChildren(pers2);
		}
		for(int i=0; i<pers1.size(); i++){
			List<Permission> children = pers1.get(i).getChildren();
			for(int j=0; j<children.size(); j++){
				List<Permission> pers3 = new ArrayList<>();
				for(int k=0; k<pers.size(); k++){
					if(children.get(j).getPermissionId().equals(pers.get(k).getParentPermissionId())){
						pers3.add(pers.get(k));
					}
				}
				children.get(j).setChildren(pers3);
			}
		}
		return pers1;
	}
}
