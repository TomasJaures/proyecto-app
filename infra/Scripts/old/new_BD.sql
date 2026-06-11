CREATE TABLE subjects (
    subject_id INT PRIMARY KEY AUTO_INCREMENT,

    subject_name VARCHAR(50),
    code VARCHAR(7)
);


CREATE TABLE modules (
    module_id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT,

    num INT, -- de 1 a 9

    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);


CREATE TABLE calendar (
    calendar_id INT PRIMARY KEY AUTO_INCREMENT
);


CREATE TABLE program (
    program_id INT PRIMARY KEY AUTO_INCREMENT
);


CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    program_id INT UNIQUE NOT NULL,
    calendar_id INT UNIQUE NOT NULL,

    user_name VARCHAR(50) NOT NULL,
    last_name1 VARCHAR(50) NOT NULL,
    last_name2 VARCHAR(50),
    mail VARCHAR(50) UNIQUE NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,

    user_role VARCHAR(15) NOT NULL, -- Professor, student... "Admin" quizas en un futuro
    -- 1:1
    FOREIGN KEY (calendar_id) REFERENCES calendar(calendar_id),
    FOREIGN KEY (program_id) REFERENCES program(program_id)
);


CREATE TABLE program_subjects (
    program_id INT NOT NULL,
    subject_id INT NOT NULL,

    FOREIGN KEY (program_id) REFERENCES program(program_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id),
    PRIMARY KEY(program_id, subject_id)
);


CREATE TABLE blocks (
    block_id INT PRIMARY KEY AUTO_INCREMENT,
    module_id INT,

    week_day ENUM('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'),
    start_hour TIME,
    end_hour TIME,
    block_state ENUM('NORMAL', 'NO_PROJECTIONS', 'COMPLETE_ANULED', 'REMOVED') DEFAULT 'NORMAL',
    FOREIGN KEY (module_id) REFERENCES modules(module_id)
);


CREATE TABLE calendar_blocks (
    calendar_id INT NOT NULL,
    block_id INT NOT NULL,

    FOREIGN KEY (calendar_id) REFERENCES calendar(calendar_id),
    FOREIGN KEY (block_id) REFERENCES blocks(block_id),
    PRIMARY KEY(calendar_id, block_id)
);


CREATE TABLE classes (
    class_id INT PRIMARY KEY AUTO_INCREMENT,
    block_id INT,
    
    is_anulled BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (block_id) REFERENCES blocks(block_id)
);


CREATE TABLE qr_token (
    qr_id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT,
    created_at DATETIME, -- Backend se encargara
    expiration_at DATETIME,
    
    -- Si QR es borrado se pierde la conexion con block (Esto es bueno, ya que solo habra 1 QR intento)
    class_id INT UNIQUE, 

    -- Puede que ON CASCADE no sea lo adecuado
    FOREIGN KEY (class_id) REFERENCES classes(class_id) ON DELETE CASCADE 
);


CREATE TABLE attendance (
    user_id INT NOT NULL,
    class_id INT NOT NULL,

    status ENUM('PRESENT', 'ABSENT', 'MANUAL_ATTENDANCE') DEFAULT 'ABSENT', 
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (class_id) REFERENCES classes(class_id),
    PRIMARY KEY(user_id, class_id)
);


CREATE TABLE unconfirmed_user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    last_name1 VARCHAR(50) NOT NULL,
    last_name2 VARCHAR(50),
    mail VARCHAR(50) UNIQUE NOT NULL, -- CRÍTICO: Debe ser único para controlar reintentos
    hashed_password VARCHAR(50) NOT NULL,
    
    attempt INT DEFAULT 0, 
    is_blocked BOOLEAN DEFAULT FALSE,
    block_time DATETIME NULL -- Cambiado a DATETIME para guardar CUÁNDO se desbloquea
);


CREATE TABLE confirmation_token (
    token_id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    expire_at DATETIME NOT NULL,
    
    user_id INT UNIQUE, -- La FK se mueve aquí. Sigue siendo 1:1 gracias al UNIQUE
    FOREIGN KEY (user_id) REFERENCES unconfirmed_user(user_id) ON DELETE CASCADE
);