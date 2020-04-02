package com.gwrocha.libary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwrocha.libary.models.GeneralConfigurations;

@Repository
public interface GeneralConfigurationsRepository extends JpaRepository<GeneralConfigurations, Long>{

	
}
