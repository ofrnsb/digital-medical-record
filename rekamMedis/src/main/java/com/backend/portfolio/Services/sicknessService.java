package com.backend.portfolio.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.portfolio.Models.Entities.SicknessEntity;
import com.backend.portfolio.Models.Repositories.sicknessRepo;

@Service
public class sicknessService {

  @Autowired
  private sicknessRepo sicknessRepo;

  public void saveSickness(SicknessEntity sicknessEntity) {
    if (sicknessEntity == null || sicknessEntity.getSicknessName() == null) {
      return;
    }

    String sicknessName = sicknessEntity.getSicknessName().trim().toLowerCase();
    Optional<SicknessEntity> existingSickness = sicknessRepo.findBySicknessName(
      sicknessName
    );
    if (existingSickness.isPresent()) {
      return;
    } else {
      sicknessEntity.setSicknessName(sicknessName);
      sicknessRepo.save(sicknessEntity);
    }
  }
}
