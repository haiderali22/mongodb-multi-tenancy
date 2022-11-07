package com.hali.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
public class TestMultiTenantcy {

	@Autowired
	PersonnelRepository personnelRepository;
	
	@Autowired
	TenantStore tenantStore;  
	
	
	@BeforeEach
	private void setup() {
//		personnelRepository.deleteAll();

	}
	
	@Test
	public void testDeleteAll() {


		Personnel p = new Personnel();
		p.setName("testFind");
		p.setTenantId("TA1");
		personnelRepository.save(p);

		Personnel p2 = new Personnel();
		p2.setTenantId("TA2"); 
		p2.setName("testFind1");
		personnelRepository.save(p2);

		tenantStore.setTenantId("TA1");
		personnelRepository.deleteAll();
		
		
		Assertions.assertEquals(1, personnelRepository.count());

		tenantStore.clear();
	}

	
	
	@Test
	public void testAdd() {

		tenantStore.setTenantId("TA");

		Personnel p = new Personnel();
		p.setName("testAdd");

		Personnel saved = personnelRepository.save(p);
		System.out.println(saved.getTenantId());

		tenantStore.clear();
	}

	@Test
	public void testFindAll() {


		Personnel p = new Personnel();
		p.setName("testFind");
		p.setTenantId("TA1");
		personnelRepository.save(p);

		Personnel p2 = new Personnel();
		p2.setTenantId("TA2"); 
		p2.setName("testFind1");
		personnelRepository.save(p2);

		tenantStore.setTenantId("TA1");
		Assertions.assertEquals(1, personnelRepository.findAll().size());

		tenantStore.clear();
	}

	@Test
	public void testFindOne() {

		Personnel p = new Personnel();
		p.setName("testFindOne");
		p.setTenantId("TA1");
		personnelRepository.save(p);

		Personnel p2 = new Personnel();
		p2.setTenantId("TA2");
		p2.setName("testFindOne1");
		Personnel saved =  personnelRepository.save(p2);

		tenantStore.setTenantId("TA1");
		Assertions.assertEquals(true, personnelRepository.findById(saved.getId()).isEmpty());

		tenantStore.clear();
	}
	
	
	@Test
	public void testFindByName() {

		Personnel p = new Personnel();
		p.setName("testFindByName");
		p.setTenantId("TA1");
		personnelRepository.save(p);

		Personnel p2 = new Personnel();
		p2.setTenantId("TA2");
		p2.setName("testFindByName1");
		Personnel saved =  personnelRepository.save(p2);

		tenantStore.setTenantId("TA1");
		Assertions.assertEquals(true, personnelRepository.findByName(saved.getName()).isEmpty());

		tenantStore.clear();
	}
}
