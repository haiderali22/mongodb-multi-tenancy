package com.hali.spring;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class BaseEntity {
	@Id
	private String id;
	private String tenantId; 
}
