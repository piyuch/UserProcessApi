INSERT INTO properties(
            key, value)
    VALUES 
    ('mail.subject.verification','Mailadresse bestätigen'),
    ('mail.sender.verification','no-reply@paylax.de'),
    ('mail.subject.resetpassword','Passwort zurücksetzen'),
    ('mail.sender.resetpassword','no-reply@paylax.de'),
    ('mail.subject.invitationtopayer','Sie wurden als Käufer eingeladen'),
    ('mail.sender.invitationtopayer','no-reply@paylax.de'),
	('jwt.ttl.auth','900'),
	('jwt.ttl.verifymail','86400'),
	('jwt.ttl.resetpassword','3600'),
	('jwt.key','lfvZAX/d3hIGXtUv7pjDxw=='),
	('url.base.webapplication','http://webapp.paylax.dev'),
	('url.path.resetpassword','/process-token/reset-pw/{token}'),
	('url.path.verificationlink','/process-token/verify-mail/{token}'),
	('url.path.reducedcontractlink','/contracts/{contractCode}/reduced'),
	('scheduler.demo.createtransactions.interval','10'),
	('commission.paylax.min','2.50'),
    ('commission.paylax.percentage','0.5');



