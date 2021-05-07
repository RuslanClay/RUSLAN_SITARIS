package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.DBFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends CrudRepository<DBFile,Long> {
}
