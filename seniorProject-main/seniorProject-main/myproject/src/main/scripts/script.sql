create table senior_project.user(
	ID int,
    username varchar(30),
    password varchar(30),
    primary key(ID)
    );
create table senior_project.account(
		ID int,
        userId int,
        balance long,
        primary key(ID),
        foreign key (userId) references user(ID)
        );

insert into senior_project.user values (1, 'lehua','root');
insert into senior_project.account values (1, 1, 1000);

insert into senior_project.account (userId, balance) values ( 1,100);
insert into senior_project.account  (userId, balance) values ( 1,300);

