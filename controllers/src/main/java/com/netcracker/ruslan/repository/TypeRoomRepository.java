package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.TypeRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRoomRepository extends CrudRepository<TypeRoom,Long> {

    boolean existsByTypeRoom(String typeRoom);
}
