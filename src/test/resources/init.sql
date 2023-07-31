

INSERT INTO AirBnBUser (AirBnBUID, DOB, Email, UserPassword, Gender, About,
                        Phone, ProfilePhotoName, ProfilePhoto, Address, Fname, MInitial, LName, Created,
                        LoginCnt, LastLogin, EmName, EmRelationship, EmPreferredLang, EmEmail, EmCountryCode, EmPhone)
VALUES (1, null, 'test@gmail.com', '***', 'M', 'Nothing about', '074563210', null, null, 'Bucuresti', 'F_NAME','M', 'L_NAME', current_date, null
        ,null,'EM_NAME', null, null,'test1@gmail.com', 'ROU', '074563210');
commit;