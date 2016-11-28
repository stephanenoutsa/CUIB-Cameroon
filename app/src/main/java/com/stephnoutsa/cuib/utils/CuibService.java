package com.stephnoutsa.cuib.utils;

import com.stephnoutsa.cuib.models.Course;
import com.stephnoutsa.cuib.models.Department;
import com.stephnoutsa.cuib.models.Lecturer;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.models.Token;
import com.stephnoutsa.cuib.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by stephnoutsa on 10/12/16.
 */
public interface CuibService {
    // Start of methods for students
    @POST("students/check")
    Call<Student> studentExists(@Body Student student);
    // End of methods for students

    // Start of methods for users
    @POST("users")
    Call<User> addUser(@Body User user);

    @PUT("users/{email}")
    Call<User> updateUser(@Path("email") String email, @Body User user);

    @POST("users/check/login")
    Call<User> loginCheck(@Body User user);
    // End of methods for users

    // Start of methods for departments
    @GET("departments/{dept}/{level}")
    Call<Department> getDepartment(@Path("dept") String dept, @Path("level") String level);
    // End of methods for departments

    // Start of methods for courses
    @GET("courses/{dept}/{level}")
    Call<Course[]> getCourses(@Path("dept") String dept, @Path("level") String level);
    // End of methods for courses

    // Start of methods for lecturers
    @GET("courses/{dept}/{level}")
    Call<Lecturer[]> getLecturers(@Path("dept") String dept, @Path("level") String level);
    // End of methods for lecturers

    // Start of methods for tokens
    @POST("tokens")
    Call<Token> addToken(@Body String token);
    // End of methods for tokens
}
