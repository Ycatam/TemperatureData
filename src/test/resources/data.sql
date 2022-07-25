
delete from temperature;
delete from city;


insert into city (id, city_name, latitude, longitude) values (1, 'sao paulo', 35.8, -10.2);
insert into city (id, city_name, latitude, longitude) values (2, 'campinas', 47, 22);

insert into temperature (date, temperature, city_id) values ('2019-02-01', 10, 1);
insert into temperature (date, temperature, city_id) values ('2019-02-02', 20, 1);
insert into temperature (date, temperature, city_id) values ('2019-02-03', 30, 1);
insert into temperature (date, temperature, city_id) values ('2019-02-04', 15, 1);
insert into temperature (date, temperature, city_id) values ('2019-02-05', 6, 1);
insert into temperature (date, temperature, city_id) values ('2019-02-06', 7, 1);
insert into temperature (date, temperature, city_id) values ('2019-02-07', 30, 1);
insert into temperature (date, temperature, city_id) values ('2019-02-08', 25, 1);
