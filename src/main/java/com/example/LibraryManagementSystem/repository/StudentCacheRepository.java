package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentCacheRepository {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public Student get(Integer studentId) {
         Object result = redisTemplate.opsForValue().get(getKey(studentId));
         return (result == null) ? null : (Student) result;

    }

    public void set(Student student) {
        redisTemplate.opsForValue().set(getKey(student.getId()),student);
    }

    private String getKey(Integer studentId) {
        return Constants.STUDENT_CACHE_KEY_PREFIX + studentId;
    }
}
