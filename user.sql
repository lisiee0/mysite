/*******************************************
*                 초기화
*******************************************/

-- 기존 테이블 삭제
drop table users;

-- 기존 SEQUENCE 삭제
drop sequence seq_users_no;

-- questbook 테이블 생성
create table users(
    no              number(5),
    id              varchar2(20) unique not null,
    password        varchar2(20) not null,
    name            varchar2(20),
    gender          varchar2(10),
    primary key(no));  

-- SEQUENCE 생성
create sequence seq_users_no
increment by 1
start with 1
nocache;

/****************************************************/

-- insert
insert into users
values(seq_users_no.nextval, 'abc', '1111', '유재석', 'male');

insert into users
values(seq_users_no.nextval, 'qwer', '1234', '이효리', 'female');


commit; 

select * from users;
