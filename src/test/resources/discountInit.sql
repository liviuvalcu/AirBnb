delete from AirBnBUser where Email = 'discount@test.com' OR Email = 'discount_G@test.com';
delete from creditcard where CardNum = 2666;
delete from bankaccount where AccountNUMBER = 2666;
delete from discount where name IN ('Silver discount test','Gold discount test','Platinum discount test');
delete from propertylisting where PID = 2666;

insert into AirBnBUser (Email, UserPassword, fname, emEmail, emCountryCode, emPhone, Phone)
        VALUES ('discount@test.com', '***', 'FNAME', 'discount@test.com', 'ROU', '072556666', '072556666');

insert into AirBnBUser (Email, UserPassword, fname, emEmail, emCountryCode, emPhone, Phone )
        VALUES ('discount_G@test.com', '***', 'FNAME', 'discount_G@test.com', 'ROU', '072556666', '072556666');

insert into creditcard(CardNum, cardholderName, cardType, CSV, ExpirationDate) VALUES (2666, 'Discount Holder', 'VISA', 1234, current_date());

INSERT INTO bankaccount (AccountNUMBER, AccountType, RoutingNum) VALUES (2666, 'DEBIT', 1);
INSERT INTO Host (AirBnBUID, BankAccountNumber) VALUES (
                                                        (select AirBnBUID from AirBnBUser where Email = 'discount@test.com'),
                                                        2666
                                                       );

INSERT INTO Guest(AirBnBUID, AvgRating, NumOfRatings, CreditCardNum) VALUES (
                               (select AirBnBUID from AirBnBUser where Email = 'discount_G@test.com'),
                                                                             0,
                                                                             0,
                                                                            2666
                           );

INSERT INTO discount (name, discount, minimNights, minimalAmountSpent,discountLevels,createdDate )  values('Silver discount test', 10, 14, 500, 'SILVER', current_date());
INSERT INTO discount (name, discount, minimNights, minimalAmountSpent,discountLevels,createdDate  )  values('Gold discount test', 15, 19, 800, 'GOLD', current_date());
INSERT INTO discount (name, discount, minimNights, minimalAmountSpent,discountLevels,createdDate  )  values('Platinum discount test', 24, 15, 900, 'PLATINUM', current_date());

INSERT INTO  propertylisting(PID, PropertyName, ZipCode, PricePerNight,Created, HID) VALUES (2666,'Discount Property',  5666, 100, current_date(), (select AirBnBUID from AirBnBUser where Email = 'discount@test.com'));