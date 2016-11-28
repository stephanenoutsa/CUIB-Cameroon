package com.stephnoutsa.cuib.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stephnoutsa.cuib.models.Course;
import com.stephnoutsa.cuib.models.Department;
import com.stephnoutsa.cuib.models.Message;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephnoutsa on 10/8/16.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private SQLiteDatabase db = null;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cuib.db";

    private static final String TABLE_STUDENT = "student";
    private static final String STUDENT_COLUMN_ID = "_sid";
    private static final String STUDENT_COLUMN_NAME = "name";
    private static final String STUDENT_COLUMN_MAT = "matricule";
    private static final String STUDENT_COLUMN_YEAR = "enrolled";
    private static final String STUDENT_COLUMN_LEVEL = "level";
    private static final String STUDENT_COLUMN_SCHOOL = "school";
    private static final String STUDENT_COLUMN_DEPT = "department";
    private static final String STUDENT_COLUMN_EMAIL = "email";
    private static final String STUDENT_COLUMN_PHONE = "phone";
    private static final String STUDENT_COLUMN_PASSWORD = "password";

    private static final String TABLE_SUBSCRIBED = "subscribed";
    private static final String SUBS_COLUMN_ID = "_sid";
    private static final String SUBS_COLUMN_STATUS = "status";

    private static final String TABLE_USER = "user";
    private static final String USER_COLUMN_ID = "_uid";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_PHONE = "phone";
    private static final String USER_COLUMN_DOB = "dob";
    private static final String USER_COLUMN_GENDER = "gender";
    private static final String USER_COLUMN_PASSWORD = "password";

    private static final String TABLE_DEPT = "department";
    private static final String DEPT_COLUMN_ID = "_did";
    private static final String DEPT_COLUMN_NAME = "name";
    private static final String DEPT_COLUMN_SCHOOL = "school";
    private static final String DEPT_COLUMN_LEVEL = "level";
    private static final String DEPT_COLUMN_TT = "timetable";

    private static final String TABLE_COURSE = "course";
    private static final String COURSE_COLUMN_ID = "_cid";
    private static final String COURSE_COLUMN_CODE = "code";
    private static final String COURSE_COLUMN_NAME = "name";
    private static final String COURSE_COLUMN_SCHOOLS = "schools";
    private static final String COURSE_COLUMN_DEPTS = "departments";
    private static final String COURSE_COLUMN_LEVELS = "levels";

    private static final String TABLE_MSG = "message";
    private static final String MSG_COLUMN_ID = "_mid";
    private static final String MSG_COLUMN_SENDER = "sender";
    private static final String MSG_COLUMN_TIME = "time";
    private static final String MSG_COLUMN_TITLE = "title";
    private static final String MSG_COLUMN_BODY = "body";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String student = "CREATE TABLE " + TABLE_STUDENT + "(" +
                STUDENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                STUDENT_COLUMN_NAME + " TEXT " + ", " +
                STUDENT_COLUMN_MAT + " TEXT " + ", " +
                STUDENT_COLUMN_YEAR + " TEXT " + ", " +
                STUDENT_COLUMN_LEVEL + " TEXT " + ", " +
                STUDENT_COLUMN_SCHOOL + " TEXT " + ", " +
                STUDENT_COLUMN_DEPT + " TEXT " + ", " +
                STUDENT_COLUMN_EMAIL + " TEXT " + ", " +
                STUDENT_COLUMN_PHONE + " TEXT " + ", " +
                STUDENT_COLUMN_PASSWORD + " TEXT " +
                ")";
        db.execSQL(student);

        // Add placeholder values
        addStudent("null", "null", "null", "null", "null", "null", "null", "null", "null");

        String subscribed = "CREATE TABLE " + TABLE_SUBSCRIBED + "(" +
                SUBS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                SUBS_COLUMN_STATUS + " TEXT " +
                ")";
        db.execSQL(subscribed);

        // Add placeholder values for Session table
        addSubscribed("pending");

        String user = "CREATE TABLE " + TABLE_USER + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                USER_COLUMN_EMAIL + " TEXT " + ", " +
                USER_COLUMN_PHONE + " TEXT " + ", " +
                USER_COLUMN_DOB + " TEXT " + ", " +
                USER_COLUMN_GENDER + " TEXT " + ", " +
                USER_COLUMN_PASSWORD + " TEXT " +
                ")";
        db.execSQL(user);

        // Add placeholder values for User table
        addUser("null", "null", "null", "null", "null");

        String dept = "CREATE TABLE " + TABLE_DEPT + "(" +
                DEPT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                DEPT_COLUMN_NAME + " TEXT " + ", " +
                DEPT_COLUMN_SCHOOL + " TEXT " + ", " +
                DEPT_COLUMN_LEVEL + " TEXT " + ", " +
                DEPT_COLUMN_TT + " TEXT " +
                ")";
        db.execSQL(dept);

        // Add placeholder values for Dept table
        addDept("null", "null", "null", "null");

        String course = "CREATE TABLE " + TABLE_COURSE + "(" +
                COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  "  + ", " +
                COURSE_COLUMN_CODE + " TEXT " + ", " +
                COURSE_COLUMN_NAME + " TEXT " + ", " +
                COURSE_COLUMN_SCHOOLS + " TEXT " + ", " +
                COURSE_COLUMN_DEPTS + " TEXT " + ", " +
                COURSE_COLUMN_LEVELS + " TEXT " +
                ")";
        db.execSQL(course);

        // Add placeholder values for Course table
        addCourse("null", "null", null, null, null);

        String msg = "CREATE TABLE " + TABLE_MSG + "(" +
                MSG_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  "  + ", " +
                MSG_COLUMN_SENDER + " TEXT " + ", " +
                MSG_COLUMN_TIME + " TEXT " + ", " +
                MSG_COLUMN_TITLE + " TEXT " + ", " +
                MSG_COLUMN_BODY + " TEXT " +
                ")";
        db.execSQL(msg);

        // Add placeholder values for Course table
        addMessage("null", "null", "null", "null");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT + ";");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIBED + ";");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPT + ";");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE + ";");
        onCreate(db);
    }

    // Add student to Student table
    public void addStudent(String name, String matricule, String enrolled, String level, String school,
                           String dept, String email, String phone, String password) {
        ContentValues values = new ContentValues();

        values.put(STUDENT_COLUMN_NAME, name);
        values.put(STUDENT_COLUMN_MAT, matricule);
        values.put(STUDENT_COLUMN_YEAR, enrolled);
        values.put(STUDENT_COLUMN_LEVEL, level);
        values.put(STUDENT_COLUMN_SCHOOL, school);
        values.put(STUDENT_COLUMN_DEPT, dept);
        values.put(STUDENT_COLUMN_EMAIL, email);
        values.put(STUDENT_COLUMN_PHONE, phone);
        values.put(STUDENT_COLUMN_PASSWORD, password);

        if(db == null) {
            db = getWritableDatabase();
        }

        db.insert(TABLE_STUDENT, null, values);
    }

    public void addStudent(Student student) {
        String name = student.getName();
        String matricule = student.getMatricule();
        String enrolled = student.getEnrolYr();
        String level = student.getLevel();
        String school = student.getSchool();
        String dept = student.getDepartment();
        String email = student.getEmail();
        String phone = student.getPhone();
        String password = student.getPassword();

        ContentValues values = new ContentValues();

        values.put(STUDENT_COLUMN_NAME, name);
        values.put(STUDENT_COLUMN_MAT, matricule);
        values.put(STUDENT_COLUMN_YEAR, enrolled);
        values.put(STUDENT_COLUMN_LEVEL, level);
        values.put(STUDENT_COLUMN_SCHOOL, school);
        values.put(STUDENT_COLUMN_DEPT, dept);
        values.put(STUDENT_COLUMN_EMAIL, email);
        values.put(STUDENT_COLUMN_PHONE, phone);
        values.put(STUDENT_COLUMN_PASSWORD, password);

        if(db == null) {
            db = getWritableDatabase();
        }

        db.insert(TABLE_STUDENT, null, values);
    }

    // Get student from Student table
    public Student getStudent() {
        String query = "SELECT * FROM " + TABLE_STUDENT + ";";
        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToLast();

        Student student = new Student();
        student.setId(Integer.parseInt(c.getString(0)));
        student.setName(c.getString(1));
        student.setMatricule(c.getString(2));
        student.setEnrolYr(c.getString(3));
        student.setLevel(c.getString(4));
        student.setSchool(c.getString(5));
        student.setDepartment(c.getString(6));
        student.setEmail(c.getString(7));
        student.setPhone(c.getString(8));
        student.setPassword(c.getString(9));

        c.close();
        return student;
    }

    // Update student in Student table
    public boolean updateStudent(String name, String matricule, String enrolled, String level,
                                 String school, String dept, String email, String phone, String password) {
        boolean ok = true;

        Student student = getStudent();

        if (student.getName().equals("null") && student.getMatricule().equals("null") &&
                student.getEnrolYr().equals("null") && student.getLevel().equals("null") &&
                student.getSchool().equals("null") && student.getDepartment().equals("null") &&
                student.getEmail().equals("null") && student.getPhone().equals("null") &&
                student.getPassword().equals("null")) {
            ok = false;
        } else {
            int id = student.getId();

            ContentValues values = new ContentValues();

            values.put(STUDENT_COLUMN_NAME, name);
            values.put(STUDENT_COLUMN_MAT, matricule);
            values.put(STUDENT_COLUMN_YEAR, enrolled);
            values.put(STUDENT_COLUMN_LEVEL, level);
            values.put(STUDENT_COLUMN_SCHOOL, school);
            values.put(STUDENT_COLUMN_DEPT, dept);
            values.put(STUDENT_COLUMN_EMAIL, email);
            values.put(STUDENT_COLUMN_PHONE, phone);
            values.put(STUDENT_COLUMN_PASSWORD, password);

            if (db == null) {
                db = getWritableDatabase();
            }

            db.update(TABLE_STUDENT, values, STUDENT_COLUMN_ID + "=" + id, null);
        }

        return ok;
    }

    // Delete student from Student table
    public void deleteStudent() {
        String query = "DELETE FROM " + TABLE_STUDENT + ";";

        if (db == null)
            db = getWritableDatabase();

        try {
            db.execSQL(query);

            // Add placeholder values for Host table
            addStudent("null", "null", "null", "null", "null", "null", "null", "null", "null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add subscribed status to Subscribed table
    public void addSubscribed(String status) {
        ContentValues values = new ContentValues();

        values.put(SUBS_COLUMN_STATUS, status);

        if (db == null)
            db = getWritableDatabase();

        db.insert(TABLE_SUBSCRIBED, null, values);
    }

    // Update subscribed status in Subscribed table
    public void updateSubscribed(String status) {
        ContentValues values = new ContentValues();

        values.put(SUBS_COLUMN_STATUS, status);

        if (db == null)
            db = getWritableDatabase();

        db.update(TABLE_SUBSCRIBED, values, SUBS_COLUMN_ID + "=1", null);
    }

    // Get subscribed status in Subscribed table
    public String getSubscribed() {
        String query = "SELECT * FROM " + TABLE_SUBSCRIBED + ";";

        if (db == null)
            db = getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToLast();
        }
        else {
            return null;
        }

        try {
            return c.getString(1);
        } finally {
            c.close();
        }
    }

    // Add user to User table
    public void addUser(String email, String phone, String dob, String gender, String password) {
        ContentValues values = new ContentValues();

        values.put(USER_COLUMN_EMAIL, email);
        values.put(USER_COLUMN_PHONE, phone);
        values.put(USER_COLUMN_DOB, dob);
        values.put(USER_COLUMN_GENDER, gender);
        values.put(USER_COLUMN_PASSWORD, password);

        if (db == null)
            db = getWritableDatabase();

        db.insert(TABLE_USER, null, values);
    }

    public void addUser(User user) {
        String email = user.getEmail();
        String phone = user.getPhone();
        String dob = user.getDob();
        String gender = user.getGender();
        String password = user.getPassword();

        ContentValues values = new ContentValues();

        values.put(USER_COLUMN_EMAIL, email);
        values.put(USER_COLUMN_PHONE, phone);
        values.put(USER_COLUMN_DOB, dob);
        values.put(USER_COLUMN_GENDER, gender);
        values.put(USER_COLUMN_PASSWORD, password);

        if (db == null)
            db = getWritableDatabase();

        db.insert(TABLE_USER, null, values);
    }

    // Get user from User table
    public User getUser() {
        if (db == null)
            db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1;";

        Cursor c = db.rawQuery(query, null);

        if (c == null)
            return null;

        c.moveToLast();

        User user = new User();
        user.setId(Integer.parseInt(c.getString(0)));
        user.setEmail(c.getString(1));
        user.setPhone(c.getString(2));
        user.setDob(c.getString(3));
        user.setGender(c.getString(4));
        user.setPassword(c.getString(5));

        try {
            return user;
        } finally {
            c.close();
        }
    }

    // Update user in User table
    public void updateUser(String email, User user) {
        ContentValues values = new ContentValues();

        values.put(USER_COLUMN_EMAIL, user.getEmail());
        values.put(USER_COLUMN_PHONE, user.getPhone());
        values.put(USER_COLUMN_DOB, user.getDob());
        values.put(USER_COLUMN_GENDER, user.getGender());
        values.put(USER_COLUMN_PASSWORD, user.getPassword());

        if (db == null)
            db = getWritableDatabase();

        db.update(TABLE_USER, values, USER_COLUMN_EMAIL + "=\'" + email + "\'", null);
    }

    // Delete user from User table
    public void deleteUser() {
        if (db == null)
            db = getWritableDatabase();

        String query = "DELETE FROM " + TABLE_USER + " WHERE 1;";
        db.execSQL(query);

        // Add placeholder values for User table
        addUser("null", "null", "null", "null", "null");
    }

    // Add dept to Dept table
    public void addDept(String name, String school, String level, String timetable) {
        ContentValues values = new ContentValues();

        values.put(DEPT_COLUMN_NAME, name);
        values.put(DEPT_COLUMN_SCHOOL, school);
        values.put(DEPT_COLUMN_LEVEL, level);
        values.put(DEPT_COLUMN_TT, timetable);

        if (db == null)
            db = getWritableDatabase();

        db.insert(TABLE_DEPT, null, values);
    }

    // Get dept from Dept table
    public Department getDept() {
        if (db == null)
            db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_DEPT + " WHERE 1;";

        Cursor c = db.rawQuery(query, null);

        if (c == null)
            return null;

        c.moveToLast();

        Department d = new Department();
        d.setId(Integer.parseInt(c.getString(0)));
        d.setName(c.getString(1));
        d.setSchool(c.getString(2));
        d.setLevel(c.getString(3));
        d.setTimetable(c.getString(4));

        try {
            return d;
        } finally {
            c.close();
        }
    }

    // Delete dept from Dept table
    public void deleteDept() {
        if (db == null)
            db = getWritableDatabase();

        String query = "DELETE FROM " + TABLE_DEPT + " WHERE 1;";
        db.execSQL(query);

        // Add placeholder values for User table
        addDept("null", "null", "null", "null");
    }

    // Add course to Course table
    public void addCourse(String code, String name, String[] schools, String [] departments, String[] levels) {
        ContentValues values = new ContentValues();

        values.put(COURSE_COLUMN_CODE, code);
        values.put(COURSE_COLUMN_NAME, name);
        values.put(COURSE_COLUMN_SCHOOLS, arrayToString(schools));
        values.put(COURSE_COLUMN_DEPTS, arrayToString(departments));
        values.put(COURSE_COLUMN_LEVELS, arrayToString(levels));

        if (db == null)
            db = getWritableDatabase();

        db.insert(TABLE_COURSE, null, values);
    }

    // Get all courses from Course table
    public List<Course> getAllCourses() {
        if (db == null)
            db = getReadableDatabase();

        List<Course> courses = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_COURSE + ";";

        Cursor c = db.rawQuery(query, null);

        if (c == null)
            return null;

        while (c.moveToNext()) {
            Course course = new Course();

            course.setId(Integer.parseInt(c.getString(0)));
            course.setCode(c.getString(1));
            course.setName(c.getString(2));
            course.setSchools(stringToArray(c.getString(3)));
            course.setDepartments(stringToArray(c.getString(4)));
            course.setLevels(stringToArray(c.getString(5)));

            courses.add(course);
        }

        try {
            return courses;
        } finally {
            c.close();
        }
    }

    // Get single course from Course table
    public Course getCourse(int id) {
        if (db == null)
            db = getReadableDatabase();

        Cursor c = db.query(TABLE_COURSE,
                new String[] {COURSE_COLUMN_ID, COURSE_COLUMN_CODE, COURSE_COLUMN_NAME, COURSE_COLUMN_SCHOOLS,
                        COURSE_COLUMN_DEPTS, COURSE_COLUMN_LEVELS},
                COURSE_COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if(c != null)
            c.moveToFirst();

        Course course = new Course();

        course.setId(Integer.parseInt(c.getString(0)));
        course.setCode(c.getString(1));
        course.setName(c.getString(2));
        course.setSchools(stringToArray(c.getString(3)));
        course.setDepartments(stringToArray(c.getString(4)));
        course.setLevels(stringToArray(c.getString(5)));

        try {
            return course;
        } finally {
            c.close();
        }
    }

    // Delete all courses from Course table
    public void deleteCourses() {
        if (db == null)
            db = getWritableDatabase();

        String query = "DELETE FROM " + TABLE_COURSE + " WHERE 1;";
        db.execSQL(query);

        // Add placeholder values for User table
        addCourse("null", "null", null, null, null);
    }

    // Add message to Message table
    public void addMessage(String sender, String time, String title, String body) {
        ContentValues values = new ContentValues();

        values.put(MSG_COLUMN_SENDER, sender);
        values.put(MSG_COLUMN_TIME, time);
        values.put(MSG_COLUMN_TITLE, title);
        values.put(MSG_COLUMN_BODY, body);

        if (db == null)
            db = getWritableDatabase();

        db.insert(TABLE_MSG, null, values);
    }

    // Get all messages from Message table
    public List<Message> getAllMessages() {
        if (db == null)
            db = getReadableDatabase();

        List<Message> messages = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_MSG + ";";

        Cursor c = db.rawQuery(query, null);

        if (c == null)
            return null;

        while (c.moveToNext()) {
            Message m = new Message();

            m.setId(Integer.parseInt(c.getString(0)));
            m.setSender(c.getString(1));
            m.setTime(c.getString(2));
            m.setTitle(c.getString(3));
            m.setBody(c.getString(4));

            messages.add(m);
        }

        try {
            return messages;
        } finally {
            c.close();
        }
    }

    // Get single message from Message table
    public Message getMessage(int id) {
        if (db == null)
            db = getReadableDatabase();

        Cursor c = db.query(TABLE_MSG,
                new String[] {MSG_COLUMN_ID, MSG_COLUMN_SENDER, MSG_COLUMN_TIME, MSG_COLUMN_TITLE, MSG_COLUMN_BODY},
                MSG_COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if(c != null)
            c.moveToFirst();

        Message message = new Message();

        message.setId(id);
        message.setSender(c.getString(1));
        message.setTime(c.getString(2));
        message.setTitle(c.getString(3));
        message.setBody(c.getString(4));

        try {
            return message;
        } finally {
            c.close();
        }
    }

    // Delete message from Message table
    public void deleteMessage(int id) {
        if (db == null)
            db = getWritableDatabase();

        String query = "DELETE FROM " + TABLE_MSG + " WHERE " + MSG_COLUMN_ID + " = " + id + ";";

        try {
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Convert array to string
    private String arrayToString(String[] array) {
        if (array != null) {
            int size = array.length;
            String string = "";

            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    string = array[0];
                } else {
                    string += ", " + array[i];
                }
            }

            return string;
        } else {
            return null;
        }
    }

    // Convert string to array
    private String[] stringToArray(String string) {
        if (string == null) {
            String[] array = {"null"};
            return array;
        }
        return string.split(", ");
    }

}
