package com.devops.groupb.harbourmaster.dao;

import java.util.List;

import com.devops.groupb.harbourmaster.dto.Pilot;

public interface PilotDAO {

    public Pilot findById(Long id);

    public Pilot save(Pilot pilot);

    public List<Pilot> findAll();

    public void deleteById(long id);

    public void delete(Pilot pilot);

    public void deleteAll();

	// public List<Pilot> findByPilotRole(PilotRole pilotRole);

    public List<Pilot> findByName(String firstName, String secondName);
    
    public Pilot findByUuid(String uuid);
}
