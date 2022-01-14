/*******************************************
*                 초기화
*******************************************/

-- 기존 테이블 삭제
drop table users;
drop table board;

-- 기존 SEQUENCE 삭제
drop sequence seq_users_no;
drop sequence seq_board_no;

-- SEQUENCE 생성
create sequence seq_users_no
increment by 1
start with 1
nocache;

create sequence seq_board_no
increment by 1
start with 1
nocache;

-- users 테이블 생성
create table users(
    no              number(5),
    id              varchar2(20) unique not null,
    password        varchar2(20) not null,
    name            varchar2(20),
    gender          varchar2(10),
    primary key(no));  

-- board 테이블 생성
create table board(
    no              number(5),
    title           varchar2(500) not null,
    content         varchar2(4000),
    hit             number(5),
    reg_date        date not null,
    user_no         number not null,
    primary key(no),
    constraint board_fk foreign key(user_no)
    references users(no)); 

/************************************************************/



insert into users
values(seq_users_no.nextval, 'abc', '1111', '유재석', 'male');

insert into users
values(seq_users_no.nextval, 'qwer', '1234', '이효리', 'female');

insert into board
values(seq_board_no.nextval, '첫번째 게시글', '첫번째', 0, sysdate, 1);

insert into board
values(seq_board_no.nextval, '두번째 게시글', '두번째', 0, sysdate, 2);

commit;

select * from users;
select * from board;


select *
from users u, board b
where u.no= b.user_no
order by reg_date desc;