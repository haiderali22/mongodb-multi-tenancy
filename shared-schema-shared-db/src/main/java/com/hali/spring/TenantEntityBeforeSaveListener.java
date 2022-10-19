package com.hali.spring;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenantEntityBeforeSaveListener extends AbstractMongoEventListener<BaseEntity>  {
	
	private final TenantStore tenantStore;  
	
	@Override
	public void onBeforeSave(BeforeSaveEvent<BaseEntity> event) {
		
		boolean isUpdate = event.getDocument().containsKey("_id");
	
		BaseEntity o = event.getSource();
		o.setTenantId(tenantStore.getTenantId());
		
		super.onBeforeSave(event);
	}
	
	@Override
	public void onAfterLoad(AfterLoadEvent<BaseEntity> event) {
		// TODO Auto-generated method stub
		super.onAfterLoad(event);
	}
}
