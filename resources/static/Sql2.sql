
-- STUDENT MANAGEMENT SYSTEM

-- 1. STUDENTS TABLE
CREATE TABLE students_sms (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    email VARCHAR2(100),
    phone VARCHAR2(15),
    password VARCHAR2(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE student_sms_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 2. ADMIN TABLE (CMS access only)
CREATE TABLE admin (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    email VARCHAR2(100),
    password VARCHAR2(100)
);

INSERT INTO admin (id, name, email, password)
VALUES (1, 'Admin', 'admin@gmail.com', 'admin123');

COMMIT;

-- 3. TEACHER TABLE (Attendance & Marks entry only) -- NEW!
CREATE TABLE teacher (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    email VARCHAR2(100),
    password VARCHAR2(100),
    subject VARCHAR2(100)
);

CREATE SEQUENCE teacher_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- Default teacher
INSERT INTO teacher (id, name, email, password, subject)
VALUES (1, 'Teacher One', 'teacher@gmail.com', 'teacher123', 'All Subjects');

COMMIT;

-- 4. ATTENDANCE TABLE
CREATE TABLE attendance (
    id NUMBER PRIMARY KEY,
    student_id NUMBER,
    attendance_date DATE,
    status VARCHAR2(10),
    CONSTRAINT fk_att_student FOREIGN KEY (student_id) REFERENCES students_sms(id)
);

CREATE SEQUENCE attendance_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 5. MARKS TABLE
CREATE TABLE marks (
    id NUMBER PRIMARY KEY,
    student_id NUMBER,
    subject VARCHAR2(100),
    marks NUMBER,
    CONSTRAINT fk_marks_student FOREIGN KEY (student_id) REFERENCES students_sms(id)
);

CREATE SEQUENCE marks_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 6. ALERTS TABLE
CREATE TABLE alerts (
    id NUMBER PRIMARY KEY,
    student_id NUMBER,
    message VARCHAR2(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alert_student FOREIGN KEY (student_id) REFERENCES students_sms(id)
);

CREATE SEQUENCE alert_seq START WITH 1 INCREMENT BY 1 NOCACHE;

COMMIT;


-- SAMPLE STUDENT DATA

INSERT INTO students_sms (id, name, email, phone, password) VALUES (1, 'Santhosh', 'santhosh@gmail.com', '9876543210', '1234');
INSERT INTO students_sms (id, name, email, phone, password) VALUES (2, 'Krish', 'krish@gmail.com', '0987654321', '1234');
INSERT INTO students_sms (id, name, email, phone, password) VALUES (3, 'Ram', 'ram@gmail.com', '1230984567', '1234');
INSERT INTO students_sms (id, name, email, phone, password) VALUES (4, 'Yash', 'yash@gmail.com', '0981237654', '1234');
INSERT INTO students_sms (id, name, email, phone, password) VALUES (5, 'Aryan', 'aryan@gmail.com', '5678093412', '1234');
INSERT INTO students_sms (id, name, email, phone, password) VALUES (6, 'Sanjay', 'sanjay@gmail.com', '5678093413', '1234');

COMMIT;




SELECT * FROM admin;
SELECT * FROM teacher;
SELECT * FROM students_sms ORDER BY id;
SELECT * FROM attendance;
SELECT * FROM marks;
SELECT * FROM alerts;



-- 1. LEAVE REQUESTS TABLE
CREATE TABLE leave_requests (
    id NUMBER PRIMARY KEY,
    student_id NUMBER,
    reason VARCHAR2(500),
    from_date DATE,
    to_date DATE,
    status VARCHAR2(20) DEFAULT 'PENDING',
    teacher_note VARCHAR2(300),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_leave_student FOREIGN KEY (student_id) REFERENCES students_sms(id)
);
CREATE SEQUENCE leave_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 2. FEE PAYMENTS TABLE
CREATE TABLE fee_payments (
    id NUMBER PRIMARY KEY,
    student_id NUMBER,
    fee_type VARCHAR2(100),
    amount NUMBER,
    status VARCHAR2(20) DEFAULT 'PENDING',
    term VARCHAR2(50),
    paid_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_fee_student FOREIGN KEY (student_id) REFERENCES students_sms(id)
);
CREATE SEQUENCE fee_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 3. ANNOUNCEMENTS TABLE
CREATE TABLE announcements (
    id NUMBER PRIMARY KEY,
    title VARCHAR2(200),
    content VARCHAR2(1000),
    posted_by VARCHAR2(100),
    priority VARCHAR2(20) DEFAULT 'NORMAL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE SEQUENCE announcement_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 4. DOUBTS TABLE
CREATE TABLE doubts (
    id NUMBER PRIMARY KEY,
    student_id NUMBER,
    subject VARCHAR2(100),
    question VARCHAR2(500),
    answer VARCHAR2(1000),
    status VARCHAR2(20) DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    answered_at TIMESTAMP,
    CONSTRAINT fk_doubt_student FOREIGN KEY (student_id) REFERENCES students_sms(id)
);
CREATE SEQUENCE doubt_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 5. STUDY MATERIALS TABLE
CREATE TABLE study_materials (
    id NUMBER PRIMARY KEY,
    title VARCHAR2(200),
    subject VARCHAR2(100),
    description VARCHAR2(500),
    file_url VARCHAR2(1000),
    file_type VARCHAR2(50),
    uploaded_by VARCHAR2(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE SEQUENCE material_seq START WITH 1 INCREMENT BY 1 NOCACHE;

COMMIT;

-- Sample data
INSERT INTO announcements (id, title, content, posted_by, priority)
VALUES (1, 'Welcome Back!', 'New semester starts today. All students please check your timetable.', 'Admin', 'IMPORTANT');

INSERT INTO fee_payments (id, student_id, fee_type, amount, status, term)
VALUES (1, 1, 'TUITION', 5000, 'PENDING', 'Term 1');

INSERT INTO fee_payments (id, student_id, fee_type, amount, status, term)
VALUES (2, 2, 'TUITION', 5000, 'PENDING', 'Term 1');

COMMIT;

DELETE FROM fee_payments;
COMMIT;

ALTER TABLE fee_payments MODIFY amount NUMBER;
COMMIT;

SELECT * from fee_payments;
