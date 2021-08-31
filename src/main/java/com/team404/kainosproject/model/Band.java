package com.team404.kainosproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "band")
public class Band {

    @Id
    @Column(name = "band_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "band")
    private List<JobRole> jobs;

    @OneToMany(mappedBy = "band")
    private List<CompetencyIndicator> competencyIndicators;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Serialise this normally, but don't serialise any reference to this in competencyIndicators
    @JsonManagedReference
    public List<CompetencyIndicator> getCompetencyIndicators() {
        return competencyIndicators;
    }

    @Override
    public String toString() {
        return "Band{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
