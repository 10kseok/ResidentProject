-- 1. 기존 테이블 삭제
drop table if exists resident;
drop table if exists birth_death_report_resident;
drop table if exists family_relationship;
drop table if exists household;
drop table if exists household_movement_address;
drop table if exists household_composition_resident;
drop table if exists certificate_issue;


-- 2. 테이블 생성
create table resident
(
   resident_serial_number       int(11)      not null,
   name                         varchar(100) not null,
   resident_registration_number varchar(300) not null,
   gender_code                  varchar(20)  not null,
   birth_date                   datetime     not null,
   birth_place_code             varchar(20)  not null,
   registration_base_address    varchar(500) not null,
   death_date                   datetime     null,
   death_place_code             varchar(20)  null,
   death_place_address          varchar(500) null,
   primary key (resident_serial_number)
);

create table birth_death_report_resident
(
   resident_serial_number           int(11)     not null,
   birth_death_type_code            varchar(20) not null,
   report_resident_serial_number    int(11)     not null,
   birth_death_report_date          date        not null,
   birth_report_qualifications_code varchar(20) null,
   death_report_qualifications_code varchar(20) null,
   email_address                    varchar(50) null,
   phone_number                     varchar(20) not null,
   primary key (resident_serial_number, birth_death_type_code)
);

create table family_relationship
(
   base_resident_serial_number   int(11)     not null,
   family_resident_serial_number int(11)     not null,
   family_relationship_code      varchar(20) not null,
   primary key (base_resident_serial_number, family_resident_serial_number)
);

create table household
(
   household_serial_number           int(11)      not null,
   household_resident_serial_number  int(11)      not null,
   household_composition_date        date         not null,
   household_composition_reason_code varchar(20)  not null,
   current_house_movement_address    varchar(500) not null,
   primary key (household_serial_number)
);

create table household_movement_address
(
   household_serial_number    int(11)      not null,
   house_movement_report_date date         not null,
   house_movement_address     varchar(500) not null,
   last_address_yn            varchar(1)   default 'Y' not null,
   primary key (household_serial_number, house_movement_report_date)
);

create table household_composition_resident
(
   household_serial_number                  int(11)     not null,
   resident_serial_number                   int(11)     not null,
   report_date                              date        not null,
   household_relationship_code              varchar(20) not null,
   household_composition_change_reason_code varchar(20) not null,
   primary key (household_serial_number, resident_serial_number)
);

create table certificate_issue
(
   certificate_confirmation_number bigint      not null,
   resident_serial_number          int         not null,
   certificate_type_code           varchar(20) not null,
   certificate_issue_date          date        not null,
   primary key (certificate_confirmation_number)
);


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
insert into birth_death_report_resident values (7, '출생', 4, '2012-03-17', '부', null, 'nam@nhnad.co.kr', '010-1234-5678');
insert into birth_death_report_resident values (1, '사망', 2, '2020-05-02', '비동거친족', null, null, '010-2345-6789');

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
insert into household_movement_address values(1, '2007-10-31', '서울시 동작구 상도로 940번길', 'N');
insert into household_movement_address values(1, '2009-10-31', '경기도 성남시 분당구 불정로 90번길', 'N');
insert into household_movement_address values(1, '2013-03-05', '경기도 성남시 분당구 대왕판교로 645번길', 'Y');

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