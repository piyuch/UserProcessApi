INSERT INTO users(
            id, createdat, updatedat, citizenship, dateofbirth, email, firstname, 
            isdeactivated, isidentified, ismailverified, isregistered, lastname, 
            needsidentification, password, pin, sex, username)
    VALUES (1, DEFAULT, DEFAULT, 'German', '1981-02-01', 'test@paynrelax.de', 'piyush', 
            FALSE, TRUE, TRUE, TRUE, 'test', 
            FALSE, 'aseasasdeasdee', 1234, 'Male', 'piyushqwer12');

INSERT INTO contracts(
            id, createdat, updatedat, amount, contractcode, description, 
            imagelink, newamount, showonmarketplace, status, title, payee_id, 
            payer_id)
    VALUES (1, DEFAULT, DEFAULT, 500.00, 'ABCDEF', 'xyz', 
            'http://abcd.de', 0, FALSE, 'PENDING', 'test', 1, 
            NULL);

INSERT INTO contract_events(
            id, createdat, updatedat, contractcode, data, eventname, eventtype, 
            isneedsaction, isread, contract_id, relateduser_id, user_id)
    VALUES (1, DEFAULT, DEFAULT, 'ABCDEF', '{"data":{"data":700.45}}', 'INFO', 'INFO', 
            FALSE, FALSE, 1, NULL, 1);
