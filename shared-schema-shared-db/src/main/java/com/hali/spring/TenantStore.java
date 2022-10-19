package com.hali.spring;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TenantStore {
	private String tenantId;

	public void clear() {
		this.tenantId = null;
	}
}
