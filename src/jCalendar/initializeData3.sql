START TRANSACTION;

INSERT INTO customer (customerId, customerName, customerPhone, customerEmail, notes, active, createDate, createdBy, lastUpdate, lastUpdateBy)
     VALUES (NULL, 'Cust 1 - Jenny', '415-423-1234', 'jenny@gmail.com', 'customer note1', 1, CURRENT_TIMESTAMP, 'test', CURRENT_TIMESTAMP, 'test');
SET @customer_id = LAST_INSERT_ID();

INSERT INTO pet (petId, customerId, petName, petType, petDescription, createDate, createdBy, lastUpdate, lastUpdateBy)  # generate ID by inserting NULL
	VALUES (NULL, @customer_id, 'Jennys Pet - Tammy', 'Kitten', 'Maine Coon',
    CURRENT_TIMESTAMP, 'test', CURRENT_TIMESTAMP, 'test'
    );
SET @pet_id = LAST_INSERT_ID();

INSERT INTO barber (barberId, barberName, notes, active, hireDate, createDate, createdBy, lastUpdate, lastUpdateBy) 
	VALUES (NULL, 'Marvin the Barber', 'barber note', 1, '2020-01-04 10:06:25',
	CURRENT_TIMESTAMP, 'test', CURRENT_TIMESTAMP, 'test'
);
SET @barber_id = LAST_INSERT_ID();
     
INSERT INTO appointment (appointmentId, title, start, end, description, type, location, month, createDate, createdBy, lastUpdate, lastUpdateBy, customerId, petId, barberId)
     VALUES (NULL, 'Standard Appt', '2020-01-11 15:00:00', '2020-01-11 15:00:30', 'Description', 'Full Service', 'location', 'January', 
     CURRENT_TIMESTAMP, 'test', CURRENT_TIMESTAMP, 'test',
     @customer_id, @pet_id, @barber_id);   # use ID in second table
SET @appointment_id = LAST_INSERT_ID();
     
INSERT INTO calendar (calendarId, name, startYear, endYear, month, weekDays, createDate, createdBy, lastUpdate, lastUpdateBy, appointmentId)
VALUES (NULL, 'mainCal', '2020', '2020', 'January', 'Saturday', 
CURRENT_TIMESTAMP, 'test', CURRENT_TIMESTAMP, 'test',
@appointment_id);

COMMIT;

