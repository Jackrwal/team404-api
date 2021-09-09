package com.team404.kainosproject.model.dto;

import com.team404.kainosproject.model.JobRole;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data transfer object abstrcting a {@link JobRole} model object.
 *
 * @author team404
 */
public class JobRoleDto {

  private final String title;
  private final String contractType;
  private final List<LocationDto> locations;
  private final String capability;
  private final String sharePointLink;
  private final int id;
  private String description;
  private String band;
  private String jobFamilyName;
  private final String responsibilities;

  /**
   * Create a new data transfer object from a model object.
   */
  public JobRoleDto(JobRole jobRole) {
    this.id = jobRole.getId();
    this.title = jobRole.getTitle();
    this.description = jobRole.getDescription();
    this.contractType = jobRole.getContractType();
    this.capability = jobRole.getCapability();
    this.band = jobRole.getBandAsString();
    this.sharePointLink = jobRole.getSharePointLink();
    this.responsibilities = jobRole.getResponsibilities();

    this.locations = jobRole.getLocations()
        .stream()
        .map(LocationDto::new)
        .collect(Collectors.toList());

    if (jobRole.getJobFamily() != null) {
      this.jobFamilyName = jobRole.getJobFamily().getName();
    }
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getContractType() {
    return contractType;
  }

  public List<LocationDto> getLocations() {
    return locations;
  }

  public String getCapability() {
    return capability;
  }

  public String getBand() {
    return band;
  }

  public void setBand(String band) {
    this.band = band;
  }

  public String getResponsibilities() {
    return responsibilities;
  }

  public String getSharePointLink() {
    return sharePointLink;
  }

  public String getJobFamilyName() {
    return jobFamilyName;
  }
}
