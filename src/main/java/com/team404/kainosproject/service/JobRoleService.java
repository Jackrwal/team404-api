package com.team404.kainosproject.service;

import com.team404.kainosproject.model.JobRole;
import com.team404.kainosproject.repository.JobRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;


@Service
public class JobRoleService {

    private static final Logger LOG = LoggerFactory.getLogger(JobRoleService.class);

    private final JobRoleRepository repository;

    @Autowired
    public JobRoleService(JobRoleRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets a List of JobRole objects from database
     * @return JobRole objects list
     */
    public Iterable<JobRole> getAll(){
        Iterable<JobRole> jobRoles = repository.findAll();
        LOG.info("Got {} JobRole entries from database", jobRoles.spliterator().estimateSize());
        return jobRoles;
    }

    /**
     * Gets an Optional of JobRole from database, based on ID
     * @param id Numeric id of Job Role in database
     * @return Optional of JobRole in database
     */
    public Optional<JobRole> getById(int id){
       return repository.findById(id);
    }

}
