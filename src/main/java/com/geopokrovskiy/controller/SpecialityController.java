package com.geopokrovskiy.controller;

import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.service.SpecialityService;

import java.util.List;

public class SpecialityController {

    private SpecialityService specialityService;

    public SpecialityController() {
        this.specialityService = new SpecialityService();
    }

    public Speciality addSpeciality(String specName){
        Speciality newSpec = new Speciality(specName);
        newSpec.setStatus(Status.ACTIVE);
        return this.specialityService.addNewSpec(newSpec);
    }

    public List<Speciality> getAllSpecs(){
        return this.specialityService.getAllSpecs();
    }

    public Speciality getSpecById(Long id){
        return this.specialityService.getSpecById(id);
    }

    public Speciality changeSpecialityName(String name, Long specId){
        return this.specialityService.updateSpecName(name, specId);
    }

    public boolean deleteSpeciality(Long id){
        return this.specialityService.delete(id);
    }
}
