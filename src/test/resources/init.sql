

CREATE TABLE test.airbnbuser(
                                  AirBnBUID INT AUTO_INCREMENT,
                                  DOB DATE,
                                  Email VARCHAR(20) NOT NULL,
                                  UserPassword VARCHAR(20) NOT NULL,
                                  Gender CHAR(1),
                                  About VARCHAR(100),
                                  Phone VARCHAR(15) NOT NULL,
                                  ProfilePhotoName VARCHAR(20),
                                  ProfilePhoto BLOB,
                                  Address VARCHAR(100),
                                  Fname VARCHAR(20) NOT NULL,
                                  MInitial VARCHAR(20),
                                  LName VARCHAR(20),
                                  Created TIMESTAMP,
                                  LoginCnt INT,
                                  LastLogin TIMESTAMP,
                                  EmName VARCHAR(20),
                                  EmRelationship VARCHAR(20),
                                  EmPreferredLang VARCHAR(15),
                                  EmEmail VARCHAR(20) NOT NULL,
                                  EmCountryCode VARCHAR(3) NOT NULL,
                                  EmPhone VARCHAR(15) NOT NULL,
                                  PRIMARY KEY(AirBnBUID));

CREATE TABLE test.propertyincludedinwishlist(
                                                  PID INT ,
                                                  AirBnBUID INT ,
                                                  WishlistName VARCHAR(50) ,
                                                  CheckInDate DATE,
                                                  CheckOutDate DATE,
                                                  modifiedFlag TINYINT(1),
                                                  PRIMARY KEY(PID,AirBnBUID,WishlistName));

CREATE TABLE test.wishlist(
                                AirBnBUID INT NOT NULL,
                                WishlistName VARCHAR(50) NOT NULL UNIQUE,
                                Privacy CHAR(1),
                                PRIMARY KEY(AirBnBUID,WishlistName));

CREATE TABLE test.creditcard(
                                  CardNum INT ,
                                  CSV INT NOT NULL,
                                  ExpirationDate DATE NOT NULL,
                                  CardholderName VARCHAR(50) NOT NULL,
                                  CardType CHAR(6) NOT NULL,
                                  Address VARCHAR(100),
                                  PRIMARY KEY(CardNum));

CREATE TABLE test.bankaccount(
                                   AccountNUMBER INT ,
                                   RoutingNum INT NOT NULL,
                                   AccountType VARCHAR(20) NOT NULL,
                                   PRIMARY KEY(AccountNUMBER));

CREATE TABLE test.guest(
                             AirBnBUID INT AUTO_INCREMENT,
                             AvgRating DECIMAL(2,1) ,
                             NumOfRatings INT DEFAULT 0,
                             CreditCardNum INT NOT NULL UNIQUE,
                             PRIMARY KEY(AirBnBUID));

CREATE TABLE test.host(
                            AirBnBUID INT AUTO_INCREMENT,
                            IsSuperHost CHAR(1) ,
                            AvgRating DECIMAL(2,1),
                            NumOfRatings INT,
                            BankAccountNumber INT NOT NULL UNIQUE,
                            PRIMARY KEY(AirBnBUID));

CREATE TABLE test.propertylisting(
                                       PID INT ,
                                       PropertyName VARCHAR(50) ,
                                       Zipcode INT NOT NULL,
                                       BathroomCnt INT,
                                       BedroomCnt INT DEFAULT 0,
                                       GuestNum INT,
                                       PricePerNight DECIMAL(6,2),
                                       CleaningFee DECIMAL(4,2),
                                       Created Date,
                                       CheckInTime TIMESTAMP,
                                       CheckOutTime TIMESTAMP,
                                       IsRefundable CHAR(1),
                                       CancellationPeriod INT,
                                       CancellationType VARCHAR(10),
                                       RefundRate DECIMAL(2,1),
                                       NumOfRatings INT DEFAULT 0,
                                       AvgRatings DECIMAL(2,1) DEFAULT 0,
                                       HID INT,
                                       Street VARCHAR(20),
                                       City VARCHAR(20),
                                       StateofResidence VARCHAR(20),
                                       Country VARCHAR(20),
                                       TaxRate DECIMAL(2,1),
                                       PRIMARY KEY(PID));

create TABLE test.accesslog (id BIGINT AUTO_INCREMENT NOT NULL, username VARCHAR(255) NOT NULL, methodName VARCHAR(255) NOT NULL, CONSTRAINT pk_log PRIMARY KEY (id));

ALTER TABLE test.propertyincludedinwishlist ADD CONSTRAINT PropertyIncludedInWishlistFK_1 FOREIGN KEY(AirBnBUID) REFERENCES test.guest(AirBnBUID) ON DELETE CASCADE ;
ALTER TABLE test.propertyincludedinwishlist ADD CONSTRAINT PropertyIncludedInWishlistFK_2 FOREIGN KEY(PID) REFERENCES test.propertylisting(PID) ON DELETE CASCADE ;
ALTER TABLE test.propertyincludedinwishlist ADD CONSTRAINT PropertyIncludedInWishlistFK_3 FOREIGN KEY(WishlistName) REFERENCES test.wishlist(WishlistName) ON DELETE CASCADE;
ALTER TABLE test.wishlist ADD CONSTRAINT WishListFK_1 FOREIGN KEY(AirBnBUID) REFERENCES test.guest(AirBnBUID) ON DELETE CASCADE;
