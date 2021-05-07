package com.netcracker.denisik.services;

import com.netcracker.denisik.entity.DBFile;
import com.netcracker.denisik.repository.DBFileRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter(onMethod_ = @Autowired)
public class DBFileService {

    private DBFileRepository fileRepository;

    @Transactional
    public void save(DBFile dbFile){
        fileRepository.save(dbFile);
    }
}
