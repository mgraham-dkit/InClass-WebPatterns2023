/**
 * Author:  michelle
 * Created: 12-Oct-2016
 *
 * Test database - used for junit tests to guarantee predictability of results
 */

drop database if exists `testDatabase`;
create database `testDatabase`;
use `testDatabase`;

/*Table structure for table employees */
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
  employeeNumber int(11) NOT NULL,
  lastName varchar(50) NOT NULL,
  firstName varchar(50) NOT NULL,
  extension varchar(10) NOT NULL,
  email varchar(100) NOT NULL,
  officeCode varchar(10) NOT NULL,
  reportsTo int(11) default NULL,
  jobTitle varchar(50) NOT NULL,
  PRIMARY KEY  (employeeNumber)
);

/*Table structure for table customers */
DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  customerNumber int(11) NOT NULL,
  customerName varchar(50) NOT NULL,
  contactLastName varchar(50) NOT NULL,
  contactFirstName varchar(50) NOT NULL,
  phone varchar(50) NOT NULL,
  addressLine1 varchar(50) NOT NULL,
  addressLine2 varchar(50) default NULL,
  city varchar(50) NOT NULL,
  `state` varchar(50) default NULL,
  postalCode varchar(15) default NULL,
  country varchar(50) NOT NULL,
  salesRepEmployeeNumber int(11) default NULL,
  creditLimit double default NULL,
  PRIMARY KEY  (customerNumber),
  FOREIGN KEY (salesRepEmployeeNumber) REFERENCES employees(employeeNumber)
);

/*Table structure for table offices */
DROP TABLE IF EXISTS offices;

CREATE TABLE offices (
  officeCode varchar(10) NOT NULL,
  city varchar(50) NOT NULL,
  phone varchar(50) NOT NULL,
  addressLine1 varchar(50) NOT NULL,
  addressLine2 varchar(50) default NULL,
  `state` varchar(50) default NULL,
  country varchar(50) NOT NULL,
  postalCode varchar(15) NOT NULL,
  territory varchar(10) NOT NULL,
  PRIMARY KEY  (officeCode)
);

/*Table structure for table productlines */
DROP TABLE IF EXISTS productlines;

CREATE TABLE productlines (
  productLine varchar(50) NOT NULL,
  textDescription varchar(4000) default NULL,
  htmlDescription mediumtext,
  image mediumblob,
  PRIMARY KEY  (productLine)
);

/*Table structure for table products */
DROP TABLE IF EXISTS products;

CREATE TABLE products (
  productCode varchar(15) NOT NULL,
  productName varchar(70) NOT NULL,
  productLine varchar(50) NOT NULL,
  productScale varchar(10) NOT NULL,
  productVendor varchar(50) NOT NULL,
  productDescription text NOT NULL,
  quantityInStock smallint(6) NOT NULL,
  buyPrice double NOT NULL,
  MSRP double NOT NULL,
  PRIMARY KEY  (productCode),
  FOREIGN KEY (productLine) REFERENCES productlines(productLine)
);

/*Table structure for table orders */
DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
  orderNumber int(11) NOT NULL,
  orderDate datetime NOT NULL,
  requiredDate datetime NOT NULL,
  shippedDate datetime default NULL,
  status varchar(15) NOT NULL,
  comments text,
  customerNumber int(11) NOT NULL,
  PRIMARY KEY  (orderNumber),
  FOREIGN KEY (customerNumber) REFERENCES customers(customerNumber)
);

/*Table structure for table orderdetails */
DROP TABLE IF EXISTS orderdetails;

CREATE TABLE orderdetails (
  orderNumber int(11) NOT NULL,
  productCode varchar(15) NOT NULL,
  quantityOrdered int(11) NOT NULL,
  priceEach double NOT NULL,
  orderLineNumber smallint(6) NOT NULL,
  PRIMARY KEY  (orderNumber,productCode),
  FOREIGN KEY (orderNumber) REFERENCES orders(orderNumber),
  FOREIGN KEY (productCode) REFERENCES products(productCode)
);

/*Table structure for table payments */
DROP TABLE IF EXISTS payments;

CREATE TABLE payments (
  customerNumber int(11) NOT NULL,
  checkNumber varchar(50) NOT NULL,
  paymentDate datetime NOT NULL,
  amount double NOT NULL,
  PRIMARY KEY  (customerNumber,checkNumber),
  FOREIGN KEY (customerNumber) references customers(customerNumber)
);

/*Data for the table `employees` */
insert  into `employees`(`employeeNumber`,`lastName`,`firstName`,`extension`,`email`,`officeCode`,`reportsTo`,`jobTitle`) values 
(1002,'Murphy','Diane','x5800','dmurphy@classicmodelcars.com','1',NULL,'President'),
(1056,'Patterson','Mary','x4611','mpatterso@classicmodelcars.com','1',1002,'VP Sales'),
(1076,'Firrelli','Jeff','x9273','jfirrelli@classicmodelcars.com','1',1002,'VP Marketing'),
(1088,'Patterson','William','x4871','wpatterson@classicmodelcars.com','6',1056,'Sales Manager (APAC)');

insert  into `customers`(`customerNumber`,`customerName`,`contactLastName`,`contactFirstName`,`phone`,`addressLine1`,`addressLine2`,`city`,`state`,`postalCode`,`country`,`salesRepEmployeeNumber`,`creditLimit`) values 
(121,'Baane Mini Imports','Bergulfsen','Jonas ','07-98 9555','Erling Skakkes gate 78',NULL,'Stavern',NULL,'4110','Norway',1056,81700), 
(124,'Mini Gifts Distributors Ltd.','Nelson','Susan','4155551450','5677 Strong St.',NULL,'San Rafael','CA','97562','USA',1056,210500),
(125,'Havel & Zbyszek Co','Piestrzeniewicz','Zbyszek ','(26) 642-7555','ul. Filtrowa 68',NULL,'Warszawa',NULL,'01-012','Poland',1076,0),
(128,'Blauer See Auto, Co.','Keitel','Roland','+49 69 66 90 2555','Lyonerstr. 34',NULL,'Frankfurt',NULL,'60528','Germany',1076,59700),
(129,'Mini Wheels Co.','Murphy','Julie','6505555787','5557 North Pendale Street',NULL,'San Francisco','CA','94217','USA',1076,64600),
(131,'Land of Toys Inc.','Lee','Kwai','2125557818','897 Long Airport Avenue',NULL,'NYC','NY','10022','USA',1056,114900),
(141,'Euro+ Shopping Channel','Freyre','Diego ','(91) 555 94 44','C/ Moralzarzal, 86',NULL,'Madrid',NULL,'28034','Spain',1056,227600);


/*Data for the table `productlines` */
insert  into `productlines`(`productLine`,`textDescription`,`htmlDescription`,`image`) values ('Vintage Cars','Our Vintage Car models realistically portray automobiles produced from the early 1900s through the 1940s. Materials used include Bakelite, diecast, plastic and wood. Most of the replicas are in the 1:18 and 1:24 scale sizes, which provide the optimum in detail and accuracy. Prices range from $30.00 up to $180.00 for some special limited edition replicas. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.',NULL,NULL),('Ships','The perfect holiday or anniversary gift for executives, clients, friends, and family. These handcrafted model ships are unique, stunning works of art that will be treasured for generations! They come fully assembled and ready for display in the home or office. We guarantee the highest quality, and best value.',NULL,NULL),('Trains','Model trains are a rewarding hobby for enthusiasts of all ages. Whether you are looking for collectible wooden trains, electric streetcars or locomotives, you will find a number of great choices for any budget within this category. The interactive aspect of trains makes toy trains perfect for young children. The wooden train sets are ideal for children under the age of 5.',NULL,NULL),('Planes','Unique, diecast airplane and helicopter replicas suitable for collections, as well as home, office or classroom decorations. Models contain stunning details such as official logos and insignias, rotating jet engines and propellers, retractable wheels, and so on. Most come fully assembled and with a certificate of authenticity from their manufacturers.',NULL,NULL),('Motorcycles','Our motorcycles are state of the art replicas of classic as well as contemporary motorcycle legends such as Harley Davidson, Ducati and Vespa. Models contain stunning details such as official logos, rotating wheels, working kickstand, front suspension, gear-shift lever, footbrake lever, and drive chain. Materials used include diecast and plastic. The models range in size from 1:10 to 1:50 scale and include numerous limited edition and several out-of-production vehicles. All models come fully assembled and ready for display in the home or office. Most include a certificate of authenticity.',NULL,NULL),('Classic Cars','Attention car enthusiasts: Make your wildest car ownership dreams come true. Whether you are looking for classic muscle cars, dream sports cars or movie-inspired miniatures, you will find great choices in this category. These replicas feature superb attention to detail and craftsmanship and offer features such as working steering system, opening forward compartment, opening rear trunk with removable spare wheel, 4-wheel independent spring suspension, and so on. The models range in size from 1:10 to 1:24 scale and include numerous limited edition and several out-of-production vehicles. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.',NULL,NULL),('Trucks and Buses','The Truck and Bus models are realistic replicas of buses and specialized trucks produced from the early 1920s to present. The models range in size from 1:12 to 1:50 scale and include numerous limited edition and several out-of-production vehicles. Materials used include tin, diecast and plastic. All models include a certificate of authenticity from their manufacturers and are a perfect ornament for the home and office.',NULL,NULL);

/*Data for the table `products` */
insert  into `products` 
values  ('S12_4473','1957 Chevy Pickup','Trucks and Buses','1:12','Exoto Designs','1:12 scale die-cast',6125,55.7,118.5),
        ('S18_3140','1903 Ford Model A','Vintage Cars','1:18','Unimax Art Galleries','Features opening trunk',3913,68.3,136.59),
        ('S18_4522','1904 Buick Runabout','Vintage Cars','1:18','Exoto Designs','Features opening trunk',8290,52.66,87.77);


/*Data for the table `orders` */
insert  into `orders`(`orderNumber`,`orderDate`,`requiredDate`,`shippedDate`,`status`,`comments`,`customerNumber`) values 
(10100,'2003-01-06 00:00:00','2003-01-13 00:00:00','2003-01-10 00:00:00','Shipped',NULL,121),
(10101,'2003-01-09 00:00:00','2003-01-18 00:00:00','2003-01-11 00:00:00','Shipped','Check on availability.',121),
(10102,'2003-01-10 00:00:00','2003-01-18 00:00:00','2003-01-14 00:00:00','Shipped',NULL,141),
(10103,'2003-01-29 00:00:00','2003-02-07 00:00:00','2003-02-02 00:00:00','Shipped',NULL,121),
(10104,'2003-01-31 00:00:00','2003-02-09 00:00:00','2003-02-01 00:00:00','Shipped',NULL,141);

/*Data for the table `orderdetails` */
insert  into `orderdetails`(`orderNumber`,`productCode`,`quantityOrdered`,`priceEach`,`orderLineNumber`) values 
(10100,'S12_4473',50,55.09,2),
(10100,'S18_3140',22,75.46,4),
(10101,'S12_4473',25,108.06,4),
(10101,'S18_4522',45,32.53,3);
