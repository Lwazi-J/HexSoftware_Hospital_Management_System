package com.example.demo.repository;

import com.example.demo.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO rooms (room_number, room_type, status, department_id, capacity, hourly_rate, floor, special_features) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM rooms";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM rooms WHERE room_id = ?";
    private static final String UPDATE_SQL = "UPDATE rooms SET room_number = ?, room_type = ?, status = ?, department_id = ?, capacity = ?, hourly_rate = ?, floor = ?, special_features = ? WHERE room_id = ?";
    private static final String DELETE_SQL = "DELETE FROM rooms WHERE room_id = ?";
    private static final String FIND_AVAILABLE_SQL = "SELECT * FROM rooms WHERE status = 'AVAILABLE'";
    private static final String FIND_BY_TYPE_SQL = "SELECT * FROM rooms WHERE room_type = ?";

    public Room save(Room room) {
        jdbcTemplate.update(INSERT_SQL,
                room.getRoomNumber(),
                room.getRoomType(),
                room.getStatus(),
                room.getDepartmentId(),
                room.getCapacity(),
                room.getHourlyRate(),
                room.getFloor(),
                room.getSpecialFeatures());
        return room;
    }

    public List<Room> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new RoomRowMapper());
    }

    public Room findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new RoomRowMapper());
    }

    public Room update(Room room) {
        jdbcTemplate.update(UPDATE_SQL,
                room.getRoomNumber(),
                room.getRoomType(),
                room.getStatus(),
                room.getDepartmentId(),
                room.getCapacity(),
                room.getHourlyRate(),
                room.getFloor(),
                room.getSpecialFeatures(),
                room.getRoomId());
        return room;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Room> findAvailableRooms() {
        return jdbcTemplate.query(FIND_AVAILABLE_SQL, new RoomRowMapper());
    }

    public List<Room> findByType(String roomType) {
        return jdbcTemplate.query(FIND_BY_TYPE_SQL,
                new Object[]{roomType},
                new RoomRowMapper());
    }

    public List<Room> findByDepartmentId(Long departmentId) {
        String sql = "SELECT * FROM rooms WHERE department_id = ?";
        return jdbcTemplate.query(sql, new Object[]{departmentId}, new RoomRowMapper());
    }

    private static final class RoomRowMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
            Room room = new Room();
            room.setRoomId(rs.getLong("room_id"));
            room.setRoomNumber(rs.getString("room_number"));
            room.setRoomType(rs.getString("room_type"));
            room.setStatus(rs.getString("status"));
            room.setDepartmentId(rs.getLong("department_id"));
            room.setCapacity(rs.getInt("capacity"));
            room.setHourlyRate(rs.getDouble("hourly_rate"));
            room.setFloor(rs.getString("floor"));
            room.setSpecialFeatures(rs.getString("special_features"));
            return room;
        }
    }
}