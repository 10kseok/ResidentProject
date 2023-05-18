
-- -----------------------------------------------------
-- Schema testdb
-- -----------------------------------------------------

DROP TABLE IF EXISTS `birth_death_report_resident`;
DROP TABLE IF EXISTS `certificate_issue`;
DROP TABLE IF EXISTS `family_relationship`;
DROP TABLE IF EXISTS `household_composition_resident`;
DROP TABLE IF EXISTS `household_movement_address`;
DROP TABLE IF EXISTS `household`;
DROP TABLE IF EXISTS `resident`;

-- -----------------------------------------------------
-- Table `resident`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `resident` (
      `resident_serial_number` INT NOT NULL,
      `name` VARCHAR(100) NOT NULL,
    `resident_registration_number` VARCHAR(300) NOT NULL,
    `gender_code` VARCHAR(20) NOT NULL,
    `birth_date` DATETIME NOT NULL,
    `birth_place_code` VARCHAR(20) NOT NULL,
    `registration_base_address` VARCHAR(500) NOT NULL,
    `death_date` DATETIME NULL DEFAULT NULL,
    `death_place_code` VARCHAR(20) NULL DEFAULT NULL,
    `death_place_address` VARCHAR(500) NULL DEFAULT NULL,
    PRIMARY KEY (`resident_serial_number`));

-- -----------------------------------------------------
-- Table `birth_death_report_resident`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `birth_death_report_resident` (
    `birth_death_type_code` VARCHAR(20) NOT NULL,
    `resident_serial_number` INT NOT NULL,
    `report_resident_serial_number` INT NOT NULL,
    `birth_death_report_date` DATE NOT NULL,
    `birth_report_qualifications_code` VARCHAR(20) NULL DEFAULT NULL,
    `death_report_qualifications_code` VARCHAR(20) NULL DEFAULT NULL,
    `email_address` VARCHAR(50) NULL DEFAULT NULL,
    `phone_number` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`birth_death_type_code`, `resident_serial_number`, `report_resident_serial_number`),
    CONSTRAINT `fk_birth_death_report_resident_resident1`
    FOREIGN KEY (`report_resident_serial_number`)
    REFERENCES `resident` (`resident_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table `certificate_issue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificate_issue` (
                                                   `certificate_confirmation_number` BIGINT NOT NULL,
                                                   `resident_serial_number` INT NOT NULL,
                                                   `certificate_type_code` VARCHAR(20) NOT NULL,
    `certificate_issue_date` DATE NOT NULL,
    PRIMARY KEY (`certificate_confirmation_number`),
    CONSTRAINT `fk_certificate_issue_resident1`
    FOREIGN KEY (`resident_serial_number`)
    REFERENCES `resident` (`resident_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



-- -----------------------------------------------------
-- Table `family_relationship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `family_relationship` (
     `family_resident_serial_number` INT NOT NULL,
     `base_resident_serial_number` INT NOT NULL,
     `family_relationship_code` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`family_resident_serial_number`, `base_resident_serial_number`),
    CONSTRAINT `fk_family_relationship_resident1`
    FOREIGN KEY (`base_resident_serial_number`)
    REFERENCES `resident` (`resident_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_family_relationship_resident2`
    FOREIGN KEY (`family_resident_serial_number`)
    REFERENCES `resident` (`resident_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
-- -----------------------------------------------------
-- Table `household`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `household` (
                                           `household_serial_number` INT NOT NULL,
                                           `household_resident_serial_number` INT NOT NULL,
                                           `household_composition_date` DATE NOT NULL,
                                           `household_composition_reason_code` VARCHAR(20) NOT NULL,
    `current_house_movement_address` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`household_serial_number`),
    CONSTRAINT `fk_household_resident1`
    FOREIGN KEY (`household_resident_serial_number`)
    REFERENCES `resident` (`resident_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table `household_composition_resident`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `household_composition_resident` (
    `household_serial_number` INT NOT NULL,
    `resident_serial_number` INT NOT NULL,
    `report_date` DATE NOT NULL,
    `household_relationship_code` VARCHAR(20) NOT NULL,
    `household_composition_change_reason_code` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`household_serial_number`, `resident_serial_number`),
    CONSTRAINT `fk_household_composition_resident_household`
    FOREIGN KEY (`household_serial_number`)
    REFERENCES `household` (`household_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_household_composition_resident_resident1`
    FOREIGN KEY (`resident_serial_number`)
    REFERENCES `resident` (`resident_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `household_movement_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `household_movement_address` (
    `house_movement_report_date` DATE NOT NULL,
    `household_serial_number` INT NOT NULL,
    `house_movement_address` VARCHAR(500) NOT NULL,
    `last_address_yn` VARCHAR(1) NOT NULL DEFAULT 'Y',
    PRIMARY KEY (`house_movement_report_date`, `household_serial_number`),
    CONSTRAINT `fk_household_movement_address_household1`
    FOREIGN KEY (`household_serial_number`)
    REFERENCES `household` (`household_serial_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- 3. resident 테이블 데이터 추가
insert into resident values(1, '남길동', '130914-1234561', '남', '1913-09-14 07:22:00', '자택', '경기도 성남시 분당구 대왕판교로645번길', '2021-04-29 09:03:00', '주택', '강원도 고성군 금강산로 290번길');
insert into resident values(2, '남석환', '540514-1234562', '남', '1954-05-14 17:30:00', '병원', '경기도 성남시 분당구 대왕판교로645번길', null, null, null);
insert into resident values(3, '박한나', '551022-1234563', '여', '1955-10-22 11:15:00', '병원', '서울특별시 중구 세종대로 110번길', null, null, null);
insert into resident values(4, '남기준', '790510-1234564', '남', '1979-05-10 20:45:00', '병원', '경기도 성남시 분당구 대왕판교로645번길', null, null, null);
insert into resident values(5, '이주은', '820821-1234565', '여', '1982-08-21 01:28:00', '병원', '경기도 수원시 팔달구 효원로 1번길', null, null, null);
insert into resident values(6, '이선미', '851205-1234566', '여', '1985-12-05 22:01:00', '병원', '경기도 수원시 팔달구 효원로 1번길', null, null, null);
insert into resident values(7, '남기석', '120315-1234567', '남', '2012-03-15 14:59:00', '병원', '경기도 성남시 분당구 대왕판교로645번길', null, null, null);

commit;


-- 4. birth_death_report_resident 테이블 데이터 추가
insert into birth_death_report_resident values ('출생', 7, 4, '2012-03-17', '부', null, 'nam@nhnad.co.kr', '010-1234-5678');
insert into birth_death_report_resident values ('사망', 1, 2, '2020-05-02', null, '비동거친족', null, '010-2345-6789');

commit;


-- 5. family_relationship 테이블 데이터 추가
insert into family_relationship values(1, 2, '자녀');
insert into family_relationship values(2, 1, '부');
insert into family_relationship values(2, 3, '배우자');
insert into family_relationship values(2, 4, '자녀');
insert into family_relationship values(3, 2, '배우자');
insert into family_relationship values(3, 4, '자녀');
insert into family_relationship values(4, 2, '부');
insert into family_relationship values(4, 3, '모');
insert into family_relationship values(4, 5, '배우자');
insert into family_relationship values(4, 7, '자녀');
insert into family_relationship values(5, 4, '배우자');
insert into family_relationship values(5, 7, '자녀');
insert into family_relationship values(7, 4, '부');
insert into family_relationship values(7, 5, '모');

commit;


-- 6. household 테이블 데이터 추가
insert into household values(1, 4, '2009-10-02', '세대분리', '경기도 성남시 분당구 대왕판교로 645번길');

commit;


-- 7. household_movement_address 테이블 데이터 추가
insert into household_movement_address values('2007-10-31', 1, '서울시 동작구 상도로 940번길', 'N');
insert into household_movement_address values('2009-10-31', 1, '경기도 성남시 분당구 불정로 90번길', 'N');
insert into household_movement_address values('2013-03-05', 1, '경기도 성남시 분당구 대왕판교로 645번길', 'Y');

commit;


-- 8. household_composition_resident 테이블 데이터 추가
insert into household_composition_resident values(1, 4, '2009-10-02', '본인', '세대분리');
insert into household_composition_resident values(1, 5, '2010-02-15', '배우자', '전입');
insert into household_composition_resident values(1, 7, '2012-03-17', '자녀', '출생등록');
insert into household_composition_resident values(1, 6, '2015-11-29', '동거인', '전입');

commit;


-- 9. certificate_issue 테이블 데이터 추가
insert into certificate_issue values(1234567891011121, 4, '가족관계증명서', '2021-10-25');
insert into certificate_issue values(9876543210987654, 4, '주민등록등본', '2021-10-25');

commit;


