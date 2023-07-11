INSERT INTO admins (name, password) VALUES ('Anna','anna');
INSERT INTO admins (name, password) VALUES ('Bob','bob');
INSERT INTO admins (name, password) VALUES ('Hans','hans');
INSERT INTO admins (name, password) VALUES ('Maria','maria');

INSERT INTO arssessions (admin_id, name) VALUES (SELECT id FROM admins WHERE admins.name = 'Hans', 'Session5');
INSERT INTO arssessions (admin_id, name) VALUES (SELECT id FROM admins WHERE admins.name = 'Maria', 'Session2');


INSERT INTO questions (question, question_user, ars_session_id) VALUES ('Wird es funktionieren', 'Hugo', SELECT id FROM arssessions WHERE arssessions.name = 'Session5');
INSERT INTO questions (question, question_user, ars_session_id) VALUES ('los', 'Test', SELECT id FROM arssessions WHERE arssessions.name = 'Session5');

INSERT INTO answers (answer, answer_user, evaluation, evaluation_user, question_id) VALUES ('Ja es funktioniert', 'Hanna', 'Sehr gut', 'Berta', SELECT id FROM questions WHERE questions.question = 'Wird es funktionieren');
INSERT INTO answers (answer, answer_user, evaluation, evaluation_user, question_id) VALUES ('Es klappt', 'Laura', 'gut', 'Leo', SELECT id FROM questions WHERE questions.question = 'Wird es funktionieren');