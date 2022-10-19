package com.hali.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMultiTenantcy {

	@Autowired
	PersonnelRepository personnelRepository;

	@Autowired
	TenantStore tenantStore;  

	@Test
	public void testAdd() {

		tenantStore.setTenantId("TA");

		Personnel p = new Personnel();
		p.setName("AAA");

		Personnel saved = personnelRepository.save(p);
		System.out.println(saved.getTenantId());

		tenantStore.clear();
	}

	@Test
	public void testFind() {


		Personnel p = new Personnel();
		p.setName("AAA1");
		p.setTenantId("TA1");
		personnelRepository.save(p);

		Personnel p2 = new Personnel();
		p2.setTenantId("TA2");
		p2.setName("AAA1");
		personnelRepository.save(p2);

		tenantStore.setTenantId("TA1");
		Assertions.assertEquals(1, personnelRepository.findAll().size());

		tenantStore.clear();
	}

	@Test
	public void testFindOne() {

		Personnel p = new Personnel();
		p.setName("AAA1");
		p.setTenantId("TA1");
		personnelRepository.save(p);

		Personnel p2 = new Personnel();
		p2.setTenantId("TA2");
		p2.setName("AAA1");
		Personnel saved =  personnelRepository.save(p2);

		tenantStore.setTenantId("TA1");
		Assertions.assertEquals(true, personnelRepository.findById(saved.getId()).isEmpty());

		tenantStore.clear();
	}
}
