package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.Facilities;
import com.netcracker.denisik.entity.TypeRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilitiesRepository extends CrudRepository<Facilities,Long> {

    List<Facilities> findAllByTypeRoomSetContains(TypeRoom typeRoom);
}
