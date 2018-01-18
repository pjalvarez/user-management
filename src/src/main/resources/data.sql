insert into role (id,name,description) values
(1, 'Administrador','Administrator role')
on duplicate key update id=id;

insert into assets (description,name) values 
('Example of an Asset',"Asset A")
on duplicate key update id=id;